package menu.skill

import common.Timer
import common.menu.SelectableWindowViewModel
import common.repository.player.PlayerRepository
import common.repository.player.PlayerRepositoryImpl
import common.status.Status
import common.values.playerNum
import menu.domain.SelectManager
import menu.repository.menustate.MenuStateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SkillViewModel : SelectableWindowViewModel(),
    KoinComponent {
    val repository: PlayerRepository = PlayerRepositoryImpl()
    private val menuStateRepository: MenuStateRepository by inject()

    override var selectManager = SelectManager(
        width = 1,
        itemNum = playerNum,
    )

    override var timer: Timer = Timer(200)

    fun getStatusAt(id: Int): Status {
        return repository.getStatus(id)
    }

    override fun pressA() {}

    override fun pressB() {
        menuStateRepository.pop()
    }
}
