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
        return field[y][x]
    }

    fun getDataAt(point: MapPoint): Int {
        return getDataAt(
            x = point.x,
            y = point.y,
        )
    }
}
