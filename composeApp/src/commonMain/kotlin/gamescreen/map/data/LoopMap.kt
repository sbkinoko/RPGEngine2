package gamescreen.map.data


import core.domain.MapConst.Companion.BOX____
import core.domain.MapConst.Companion.GLASS__
import core.domain.MapConst.Companion.ROAD___
import core.domain.MapConst.Companion.TOWN_1I
import core.domain.MapConst.Companion.WATER__
import gamescreen.map.domain.MapData

class LoopMap : MapData() {
    override val isLoop = true
    override val width: Int
        get() = 10
    override val height: Int
        get() = 10
    override val field: Array<Array<Int>>
        get() = arrayOf(
            arrayOf(
                GLASS__,
                GLASS__,
                GLASS__,
                GLASS__,
                GLASS__,
                WATER__,
                WATER__,
                WATER__,
                WATER__,
                WATER__
            ),
            arrayOf(
                GLASS__,
                GLASS__,
                GLASS__,
                GLASS__,
                GLASS__,
                WATER__,
                WATER__,
                WATER__,
                WATER__,
                WATER__
            ),
            arrayOf(
                GLASS__,
                GLASS__,
                BOX____,
                GLASS__,
                GLASS__,
                WATER__,
                WATER__,
                WATER__,
                WATER__,
                WATER__
            ),
            arrayOf(
                GLASS__,
                GLASS__,
                GLASS__,
                GLASS__,
                GLASS__,
                WATER__,
                WATER__,
                WATER__,
                WATER__,
                WATER__
            ),
            arrayOf(
                GLASS__,
                GLASS__,
                GLASS__,
                GLASS__,
                GLASS__,
                WATER__,
                WATER__,
                WATER__,
                WATER__,
                WATER__
            ),
            arrayOf(
                GLASS__,
                GLASS__,
                GLASS__,
                GLASS__,
                GLASS__,
                ROAD___,
                GLASS__,
                GLASS__,
                GLASS__,
                GLASS__
            ),
            arrayOf(
                GLASS__,
                GLASS__,
                ROAD___,
                ROAD___,
                ROAD___,
                ROAD___,
                ROAD___,
                ROAD___,
                GLASS__,
                GLASS__
            ),
            arrayOf(
                ROAD___,
                ROAD___,
                ROAD___,
                GLASS__,
                GLASS__,
                ROAD___,
                GLASS__,
                ROAD___,
                GLASS__,
                GLASS__
            ),
            arrayOf(
                ROAD___,
                ROAD___,
                ROAD___,
                GLASS__,
                GLASS__,
                ROAD___,
                ROAD___,
                ROAD___,
                GLASS__,
                GLASS__
            ),
            arrayOf(
                GLASS__,
                GLASS__,
                ROAD___,
                ROAD___,
                TOWN_1I,
                ROAD___,
                GLASS__,
                GLASS__,
                GLASS__,
                GLASS__
            ),
        )
}
