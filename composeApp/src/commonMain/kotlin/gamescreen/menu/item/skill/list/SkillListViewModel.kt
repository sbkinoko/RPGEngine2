package gamescreen.menu.item.skill.list

import core.domain.AbleType
import core.domain.Place
import core.repository.item.skill.SkillRepository
import core.text.repository.TextRepository
import core.usecase.checkcanuseskill.CheckCanUseSkillUseCase
import gamescreen.menu.domain.MenuType
import gamescreen.menu.item.abstract.itemselect.ItemListViewModel
import gamescreen.menu.item.skill.repository.useid.UseSkillIdRepository
import gamescreen.menu.repository.menustate.MenuStateRepository
import org.koin.core.component.inject

class SkillListViewModel : ItemListViewModel() {
    private val menuStateRepository: MenuStateRepository by inject()
    private val useSkillIdRepository: UseSkillIdRepository by inject()

    private val checkCanUseSkillUseCase: CheckCanUseSkillUseCase by inject()

    private val textRepository: TextRepository by inject()

    override val itemRepository: SkillRepository by inject()

    override val boundedScreenType: MenuType
        get() = MenuType.SKILL_LST

    override fun goNextImpl() {
        val skillId = itemList[selectManager.selected]

        val status = playerRepository.getStatus(userId)

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

    override fun getPlayerItemListAt(id: Int): List<Int> {
        return playerRepository.getStatus(id).skillList
    }
}
