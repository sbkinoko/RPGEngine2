package gamescreen.map.usecase.event.cellevent

import core.domain.mapcell.CellType

interface CellEventUseCase {
    /**
     * 対応するマスのイベントを発火
     */
    suspend operator fun invoke(
        cellId: CellType,
    )
}
