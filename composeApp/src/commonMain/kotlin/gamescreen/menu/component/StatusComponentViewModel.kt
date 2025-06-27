package gamescreen.menu.component

import core.repository.player.PlayerStatusRepository
import core.repository.statusdata.StatusDataRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StatusComponentViewModel(
    statusDataRepository: StatusDataRepository,
) : KoinComponent {
    private val playerStatusRepository: PlayerStatusRepository by inject()

    val statusFlow = playerStatusRepository
        .playerStatusFlow

    val statusDataFlow = statusDataRepository.statusDataFlow
}
