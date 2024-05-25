package domain.map

class MapData {

    val isLoop: Boolean = true

    val width: Int
        get() = 10

    val height: Int
        get() = 10

    private val field = Array(height) { y ->
        Array(width) { x ->
            if (x < 5 && y < 5) {
                2
            } else {
                1
            }
        }
    }

    fun getDataAt(
        x: Int,
        y: Int,
    ): Int {
        if (y < 0 || field.size <= y ||
            x < 0 || field[0].size <= x
        ) {
            return 0
        }

        return field[y][x]
    }

    fun getDataAt(point: MapPoint): Int {
        return getDataAt(
            x = point.x,
            y = point.y,
        )
    }
}
