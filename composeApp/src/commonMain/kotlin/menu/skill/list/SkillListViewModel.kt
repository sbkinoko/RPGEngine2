package menu.skill.list

import common.Timer
import common.values.playerNum
import main.repository.player.PlayerRepository
import menu.MenuChildViewModel
import menu.domain.MenuType
import menu.domain.SelectManager
import menu.repository.menustate.MenuStateRepository
import menu.skill.repository.skilluser.SkillUserRepository
import menu.skill.repository.useid.UseSkillIdRepository
import menu.skill.usecase.getskillexplain.GetSkillExplainUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SkillListViewModel : MenuChildViewModel(),
    KoinComponent {
    val repository: PlayerRepository by inject()
    private val menuStateRepository: MenuStateRepository by inject()
    private val skillUserRepository: SkillUserRepository by inject()
    private val useSkillIdRepository: UseSkillIdRepository by inject()

    private val getSkillExplainUseCase: GetSkillExplainUseCase by inject()

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
        useSkillIdRepository.skillId = selectManager.selected.toSkillId()
        menuStateRepository.push(
            MenuType.SKILL_TARGET,
        )
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
        return getSkillExplainUseCase.invoke(skillId)
    }

    override fun pressB() {
        menuStateRepository.pop()
    }
}
