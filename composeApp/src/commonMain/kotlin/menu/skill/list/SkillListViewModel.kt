package menu.skill.list

import common.Timer
import common.values.playerNum
import main.repository.player.PlayerRepository
import main.repository.skill.SkillRepository
import menu.MenuChildViewModel
import menu.domain.MenuType
import menu.domain.SelectManager
import menu.repository.menustate.MenuStateRepository
import menu.repository.skilluser.SkillUserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SkillListViewModel : MenuChildViewModel(),
    KoinComponent {
    val repository: PlayerRepository by inject()
    private val skillRepository: SkillRepository by inject()
    private val menuStateRepository: MenuStateRepository by inject()
    private val skillUserRepository: SkillUserRepository by inject()

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
        TODO("Not yet implemented")
    }

    private fun loadSkill(userId: Int) {
        selectManager = SelectManager(
            width = 1,
            itemNum = repository.getStatus(userId).skillList.size,
        )
        selectManager.selected = 0
    }

    fun getExplainAt(id: Int): String {
        val skillId = repository.getStatus(user).skillList[id]
        val explain = skillRepository.getSkill(skillId).name
        return explain
    }

    override fun pressB() {
        menuStateRepository.pop()
    }
}
