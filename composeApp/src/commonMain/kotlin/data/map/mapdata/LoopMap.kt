package data.map.mapdata

import domain.map.MapData

class LoopMap : MapData() {
    override val isLoop = true
    override val width: Int
        get() = 10
    override val height: Int
        get() = 10
    override val field: Array<Array<Int>>
        get() = Array(height) { y ->
            Array(width) { x ->
                if (
                    x == 5 &&
                    y == 5
                ) {
                    3
                } else if (
                    x < 5 &&
                    5 <= y
                ) {
                    2
                } else {
                    1
                }
            }
        }
}
