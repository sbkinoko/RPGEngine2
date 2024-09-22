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

    override fun goNextImpl() {
        TODO("Not yet implemented")
    }

    fun getNameAt(id: Int): String {
        return repository.getStatus(id).name
    }

    fun getSkillAt(id: Int): List<Int> {
        return repository.getStatus(id).skillList
    }

    fun getSkillName(id: Int): String {
        return skillRepository.getSkill(id).name
    }

    override fun pressB() {
        menuStateRepository.pop()
    }
}
