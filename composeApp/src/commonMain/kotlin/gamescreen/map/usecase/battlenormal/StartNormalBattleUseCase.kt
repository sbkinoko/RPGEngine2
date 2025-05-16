package gamescreen.map.usecase.battlenormal

import gamescreen.map.domain.MapUiState

interface StartNormalBattleUseCase {
    operator fun invoke(
        mapUiState: MapUiState,
        updateScreen: (MapUiState) -> Unit,
    )
}
