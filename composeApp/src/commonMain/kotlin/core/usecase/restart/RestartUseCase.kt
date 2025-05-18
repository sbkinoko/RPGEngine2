package core.usecase.restart

import gamescreen.map.domain.MapUiState

interface RestartUseCase {
    suspend operator fun invoke(
        mapUiState: MapUiState,
    ): MapUiState
}
