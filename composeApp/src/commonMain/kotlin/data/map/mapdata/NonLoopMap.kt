package data.map.mapdata

import domain.map.MapData

class NonLoopMap : MapData() {
    override val isLoop = false
    override val width: Int
        get() = 10
    override val height: Int
        get() = 10
    override val field: Array<Array<Int>>
        get() = Array(height) { y ->
            Array(width) { x ->
                if (x < 5 && 5 <= y) {
                    2
                } else {
                    1
                }
            }
        }
}
