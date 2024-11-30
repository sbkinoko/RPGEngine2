package gamescreen.map.data

import core.domain.mapcell.CellType
import gamescreen.map.domain.MapData

class NonLoopTestMap : MapData() {
    override val isLoop = false
    override val width: Int
        get() = 4
    override val height: Int
        get() = 4
    override val field: Array<Array<CellType>>
        get() = Array(height) { y ->
            Array(width) { x ->
                CellType.TextCell(
                    id = x + width * y,
                )
            }
        }
}
