package gamescreen.map.usecase.event.cellevent

import core.domain.mapcell.CellType
import gamescreen.map.domain.UIData

interface CellEventUseCase {
    /**
     * 対応するマスのイベントを発火
     */
    suspend operator fun invoke(
        cellId: CellType,
    ): UIData?
}
