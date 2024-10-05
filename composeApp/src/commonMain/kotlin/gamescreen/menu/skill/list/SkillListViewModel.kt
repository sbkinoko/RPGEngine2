package menu.skill.list

import common.Timer
import common.values.playerNum
import core.domain.AbleType
import core.domain.Place
import core.repository.item.skill.SkillRepository
import core.repository.player.PlayerRepository
import core.text.repository.TextRepository
import core.usecase.checkcanuseskill.CheckCanUseSkillUseCase
import menu.MenuChildViewModel
import menu.domain.MenuType
import menu.domain.SelectManager
import menu.repository.menustate.MenuStateRepository
import menu.skill.repository.skilluser.SkillUserRepository
import menu.skill.repository.useid.UseSkillIdRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SkillListViewModel : MenuChildViewModel(),
    KoinComponent {
    val repository: PlayerRepository by inject()
    private val menuStateRepository: MenuStateRepository by inject()
    private val skillUserRepository: SkillUserRepository by inject()
    private val useSkillIdRepository: UseSkillIdRepository by inject()
    private val skillRepository: SkillRepository by inject()

    private val checkCanUseSkillUseCase: CheckCanUseSkillUseCase by inject()

    private val textRepository: TextRepository by inject()

    override var selectManager = SelectManager(
        width = 1,
        itemNum = playerNum,
    )
    override val canBack: Boolean
        get() = true

    override var timer: Timer = Timer(200)

    override fun isBoundedImpl(commandType: MenuType): Boolean {
        return commandType == MenuType.SKILL_LST
    }

    val user: Int
        get() = skillUserRepository.skillUserId

    fun init() {
        loadSkill(user)
    }

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

    private fun loadSkill(userId: Int) {
        selectManager = SelectManager(
            width = 1,
            itemNum = repository.getStatus(userId).skillList.size,
        )
        selectManager.selected = 0
    }

    private fun Int.toSkillId(): Int {
        return repository.getStatus(user).skillList[this]
    }

    fun getExplainAt(position: Int): String {
        val skillId = position.toSkillId()
        return skillRepository.getSkill(skillId).explain
    }

    override fun pressB() {
        menuStateRepository.pop()
    }
}
