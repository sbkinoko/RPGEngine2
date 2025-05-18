package gamescreen.map.usecase.event.cellevent

import core.domain.mapcell.CellType
import gamescreen.map.domain.MapUiState

interface CellEventUseCase {
    /**
     * 対応するマスのイベントを発火
     */
    suspend operator fun invoke(
        cellId: CellType,
        mapUiState: MapUiState,
    ): MapUiState
}
