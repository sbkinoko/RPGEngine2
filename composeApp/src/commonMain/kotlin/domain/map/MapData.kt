package domain.map

class MapData {

    private val field = arrayOf(
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        arrayOf(2, 2, 2, 2, 2, 1, 1, 1, 1, 1),
        arrayOf(2, 2, 2, 2, 2, 1, 1, 1, 1, 1),
        arrayOf(2, 2, 2, 2, 2, 1, 1, 1, 1, 1),
        arrayOf(2, 2, 2, 2, 2, 1, 1, 1, 1, 1),
        arrayOf(2, 2, 2, 2, 2, 1, 1, 1, 1, 1),
    )

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
