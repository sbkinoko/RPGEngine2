package gamescreen.menu.status

import common.Timer
import core.domain.status.Character
import core.menu.SelectableWindowViewModel
import core.repository.player.PlayerStatusRepository
import gamescreen.menu.domain.SelectManager
import gamescreen.menu.repository.menustate.MenuStateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.Constants

class StatusViewModel : SelectableWindowViewModel(),
    KoinComponent {
    val repository: PlayerStatusRepository by inject()
    private val menuStateRepository: MenuStateRepository by inject()

    override var selectManager = SelectManager(
        width = 1,
        itemNum = Constants.playerNum,
    )

    override var timer: Timer = Timer(200)

    fun getStatusAt(id: Int): Character {
        return repository.getStatus(id)
    }

    override fun pressA() {}

    override fun pressB() {
        menuStateRepository.pop()
    }
}
