package menu.skill

import common.Timer
import common.values.playerNum
import main.menu.SelectableWindowViewModel
import main.repository.player.PlayerRepository
import main.repository.skill.SkillRepository
import menu.domain.SelectManager
import menu.repository.menustate.MenuStateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SkillViewModel : SelectableWindowViewModel(),
    KoinComponent {
    val repository: PlayerRepository by inject()
    private val skillRepository: SkillRepository by inject()
    private val menuStateRepository: MenuStateRepository by inject()

    override var selectManager = SelectManager(
        width = 1,
        itemNum = playerNum,
    )

    override var timer: Timer = Timer(200)

    fun getNameAt(id: Int): String {
        return repository.getStatus(id).name
    }

    fun getSkillAt(id: Int): List<Int> {
        return repository.getStatus(id).skillList
    }

    fun getSkillName(id: Int): String {
        return skillRepository.getSkill(id).name
    }

    override fun pressA() {}

    override fun pressB() {
        menuStateRepository.pop()
    }
}
