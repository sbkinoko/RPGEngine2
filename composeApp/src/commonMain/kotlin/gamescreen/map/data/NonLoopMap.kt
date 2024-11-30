package gamescreen.map.data

import core.domain.mapcell.CellType
import gamescreen.map.domain.MapData

class NonLoopMap : MapData() {
    override val isLoop = false
    override val width: Int
        get() = 10
    override val height: Int
        get() = 10
    override val field: Array<Array<CellType>>
        get() = Array(height) { y ->
            Array(width) { x ->
                if (x == 0 && y == 2) {
                    CellType.Town1O
                } else if (x == 0 || x == 9 ||
                    y == 0 || y == 9
                ) {
                    CellType.Water
                } else {
                    CellType.Glass
                }
            }
        }
}
