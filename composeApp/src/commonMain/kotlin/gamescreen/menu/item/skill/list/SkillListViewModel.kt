package gamescreen.menu.item.skill.list

import common.Timer
import common.values.playerNum
import core.domain.AbleType
import core.domain.Place
import core.repository.item.skill.SkillRepository
import core.repository.player.PlayerRepository
import core.text.repository.TextRepository
import core.usecase.checkcanuseskill.CheckCanUseSkillUseCase
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.abstract.itemselect.ItemListViewModel
import gamescreen.menu.item.skill.repository.skilluser.SkillUserRepository
import gamescreen.menu.item.skill.repository.useid.UseSkillIdRepository
import gamescreen.menu.repository.menustate.MenuStateRepository
import org.koin.core.component.inject

class SkillListViewModel : ItemListViewModel() {
    val repository: PlayerRepository by inject()
    private val menuStateRepository: MenuStateRepository by inject()
    private val skillUserRepository: SkillUserRepository by inject()
    private val useSkillIdRepository: UseSkillIdRepository by inject()

    private val checkCanUseSkillUseCase: CheckCanUseSkillUseCase by inject()

    private val textRepository: TextRepository by inject()

    override val itemRepository: SkillRepository by inject()

    override var selectManager = SelectManager(
        width = 1,
        itemNum = playerNum,
    )
    override val canBack: Boolean
        get() = true
    override val itemList: List<Int>
        get() = repository.getStatus(user).skillList

    override val boundedScreenType: MenuType
        get() = MenuType.SKILL_LST

    override var timer: Timer = Timer(200)

    val user: Int
        get() = skillUserRepository.skillUserId

    override fun goNextImpl() {
        val skillId = selectManager.selected.toSkillId()
        val status = repository.getStatus(user)

        val ableType = checkCanUseSkillUseCase.invoke(
            skillId = skillId,
            status = status,
            here = Place.MAP,
        )
        when (ableType) {
            AbleType.Able -> {
                // skillIdを保存
                useSkillIdRepository.skillId = skillId
                //　次の画面に遷移
                menuStateRepository.push(
                    MenuType.SKILL_TARGET,
                )
            }

            AbleType.CANT_USE_BY_PLACE -> {
                textRepository.setText("ここでは使えません")
                textRepository.push(true)
            }

            AbleType.CANT_USE_BY_MP -> {
                textRepository.setText("MPがたりません")
                textRepository.push(true)
            }
        }
    }

    private fun Int.toSkillId(): Int {
        return repository.getStatus(user).skillList[this]
    }

    override fun getPlayerItemListAt(id: Int): List<Int> {
        return repository.getStatus(id).skillList
    }
}
