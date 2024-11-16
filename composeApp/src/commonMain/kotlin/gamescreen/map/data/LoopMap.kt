package gamescreen.map.data

import gamescreen.map.domain.MapData

class LoopMap : MapData() {
    override val isLoop = true
    override val width: Int
        get() = 10
    override val height: Int
        get() = 10
    override val field: Array<Array<Int>>
        get() = arrayOf(
            arrayOf(1, 1, 1, 1, 1, 2, 2, 2, 2, 2),
            arrayOf(1, 1, 1, 1, 1, 2, 2, 2, 2, 2),
            arrayOf(1, 1, 6, 1, 1, 2, 2, 2, 2, 2),
            arrayOf(1, 1, 1, 1, 1, 2, 2, 2, 2, 2),
            arrayOf(1, 1, 1, 1, 1, 2, 2, 2, 2, 2),
            arrayOf(1, 1, 1, 1, 1, 5, 1, 1, 1, 1),
            arrayOf(1, 1, 5, 5, 5, 5, 5, 5, 1, 1),
            arrayOf(5, 5, 5, 1, 1, 5, 1, 5, 1, 1),
            arrayOf(5, 5, 5, 1, 1, 5, 5, 5, 1, 1),
            arrayOf(1, 1, 5, 5, 3, 5, 1, 1, 1, 1),
        )
}
