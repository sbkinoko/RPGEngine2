package gamescreen.menu.component

import core.repository.player.PlayerStatusRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StatusComponentViewModel : KoinComponent {
    private val playerStatusRepository: PlayerStatusRepository by inject()

    val statusFlow = playerStatusRepository
        .playerStatusFlow
}
