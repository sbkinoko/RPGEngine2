package map.data

import map.domain.MapData

class NonLoopTestMap : MapData() {
    override val isLoop = false
    override val width: Int
        get() = 4
    override val height: Int
        get() = 4
    override val field: Array<Array<Int>>
        get() = Array(height) { y ->
            Array(width) { x ->
                x + width * y
            }
        }
}
