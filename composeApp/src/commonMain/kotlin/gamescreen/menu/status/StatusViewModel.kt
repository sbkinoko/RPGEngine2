package gamescreen.menu.status

import common.Timer
import common.values.playerNum
import core.domain.status.Status
import core.menu.SelectableWindowViewModel
import core.repository.player.PlayerRepository
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.repository.menustate.MenuStateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StatusViewModel : SelectableWindowViewModel(),
    KoinComponent {
    val repository: PlayerRepository by inject()
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
