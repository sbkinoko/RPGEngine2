package gamescreen.menu.component

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StatusComponentViewModel(
    statusDataRepository: core.repository.memory.character.statusdata.StatusDataRepository,
) : KoinComponent {
    private val playerStatusRepository: core.repository.memory.character.player.PlayerCharacterRepository by inject()

    val statusFlow = playerStatusRepository
        .playerStatusFlow

    val statusDataFlow = statusDataRepository.statusDataFlow
}
