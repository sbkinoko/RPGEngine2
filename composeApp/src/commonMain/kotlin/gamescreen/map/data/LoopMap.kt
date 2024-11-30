package gamescreen.map.data


import core.domain.mapcell.CellType
import gamescreen.map.domain.MapData

class LoopMap : MapData() {
    override val isLoop = true
    override val width: Int
        get() = 10
    override val height: Int
        get() = 10
    override val field: Array<Array<CellType>>
        get() = arrayOf(
            arrayOf(
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water
            ),
            arrayOf(
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water
            ),
            arrayOf(
                CellType.Glass,
                CellType.Glass,
                CellType.Box,
                CellType.Glass,
                CellType.Glass,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water
            ),
            arrayOf(
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water
            ),
            arrayOf(
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water,
                CellType.Water
            ),
            arrayOf(
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Road,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass
            ),
            arrayOf(
                CellType.Glass,
                CellType.Glass,
                CellType.Road,
                CellType.Road,
                CellType.Road,
                CellType.Road,
                CellType.Road,
                CellType.Road,
                CellType.Glass,
                CellType.Glass
            ),
            arrayOf(
                CellType.Road,
                CellType.Road,
                CellType.Road,
                CellType.Glass,
                CellType.Glass,
                CellType.Road,
                CellType.Glass,
                CellType.Road,
                CellType.Glass,
                CellType.Glass
            ),
            arrayOf(
                CellType.Road,
                CellType.Road,
                CellType.Road,
                CellType.Glass,
                CellType.Glass,
                CellType.Road,
                CellType.Road,
                CellType.Road,
                CellType.Glass,
                CellType.Glass
            ),
            arrayOf(
                CellType.Glass,
                CellType.Glass,
                CellType.Road,
                CellType.Road,
                CellType.Town1I,
                CellType.Road,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass
            ),
        )
}
