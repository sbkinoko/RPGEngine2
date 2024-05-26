package domain.map

abstract class MapData {

    abstract val isLoop: Boolean

    abstract val width: Int

    abstract val height: Int

    abstract val field: Array<Array<Int>>

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
