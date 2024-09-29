package map.data

import map.domain.MapData

class NonLoopMap : MapData() {
    override val isLoop = false
    override val width: Int
        get() = 10
    override val height: Int
        get() = 10
    override val field: Array<Array<Int>>
        get() = Array(height) { y ->
            Array(width) { x ->
                if (x == 0 && y == 2) {
                    4
                } else if (x == 0 || x == 9 ||
                    y == 0 || y == 9
                ) {
                    2
                } else {
                    1
                }
            }
        }
}
