package gamescreen.map.data

import gamescreen.map.domain.MapData

class LoopTestMap : MapData() {
    override val isLoop = true
    override val width: Int
        get() = 4
    override val height: Int
        get() = 4
    override val field: Array<Array<Any>>
        get() = Array(height) { y ->
            Array(width) { x ->
                x + width * y
            }
        }
}
