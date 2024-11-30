package gamescreen.map.data


import core.domain.mapcell.CellType
import core.domain.mapcell.MapConst.Companion.BOX____
import core.domain.mapcell.MapConst.Companion.ROAD___
import core.domain.mapcell.MapConst.Companion.TOWN_1I
import gamescreen.map.domain.MapData

class LoopMap : MapData() {
    override val isLoop = true
    override val width: Int
        get() = 10
    override val height: Int
        get() = 10
    override val field: Array<Array<Any>>
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
                BOX____,
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
                ROAD___,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass
            ),
            arrayOf(
                CellType.Glass,
                CellType.Glass,
                ROAD___,
                ROAD___,
                ROAD___,
                ROAD___,
                ROAD___,
                ROAD___,
                CellType.Glass,
                CellType.Glass
            ),
            arrayOf(
                ROAD___,
                ROAD___,
                ROAD___,
                CellType.Glass,
                CellType.Glass,
                ROAD___,
                CellType.Glass,
                ROAD___,
                CellType.Glass,
                CellType.Glass
            ),
            arrayOf(
                ROAD___,
                ROAD___,
                ROAD___,
                CellType.Glass,
                CellType.Glass,
                ROAD___,
                ROAD___,
                ROAD___,
                CellType.Glass,
                CellType.Glass
            ),
            arrayOf(
                CellType.Glass,
                CellType.Glass,
                ROAD___,
                ROAD___,
                TOWN_1I,
                ROAD___,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass,
                CellType.Glass
            ),
        )
}
