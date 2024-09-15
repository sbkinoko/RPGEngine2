package menu.status

import common.Timer
import common.values.playerNum
import main.menu.SelectableWindowViewModel
import main.repository.player.PlayerRepository
import main.repository.player.PlayerRepositoryImpl
import main.status.Status
import menu.domain.SelectManager
import menu.repository.menustate.MenuStateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StatusViewModel : SelectableWindowViewModel(),
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
