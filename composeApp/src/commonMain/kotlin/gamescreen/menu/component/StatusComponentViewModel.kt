package gamescreen.menu.component

import core.repository.character.player.PlayerCharacterRepository
import core.repository.character.statusdata.StatusDataRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StatusComponentViewModel(
    statusDataRepository: StatusDataRepository,
) : KoinComponent {
    private val playerStatusRepository: PlayerCharacterRepository by inject()

    val statusFlow = playerStatusRepository
        .playerStatusFlow

    val statusDataFlow = statusDataRepository.statusDataFlow
}
