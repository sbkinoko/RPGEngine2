package gamescreen.menu.item.skill.user

import common.Timer
import common.values.playerNum
import core.repository.player.PlayerRepository
import core.repository.skill.SkillRepository
import gamescreen.menu.MenuChildViewModel
import gamescreen.menu.domain.MenuType
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.item.skill.repository.skilluser.SkillUserRepository
import gamescreen.menu.repository.menustate.MenuStateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SkillUserViewModel : MenuChildViewModel(),
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
        return commandType == MenuType.SKILL_USER
    }

    override fun goNextImpl() {
        skillUserRepository.skillUserId = selectManager.selected
        menuStateRepository.push(MenuType.SKILL_LST)
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
