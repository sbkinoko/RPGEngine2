package gamescreen.map.domain

abstract class MapData {

    abstract val isLoop: Boolean

    abstract val width: Int

    abstract val height: Int

    abstract val field: Array<Array<Any>>

    fun getDataAt(
        x: Int,
        y: Int,
    ): Any {
        if (y < 0 || field.size <= y ||
            x < 0 || field[0].size <= x
        ) {
            return 0
        }

        return field[y][x]
    }

    fun getDataAt(point: MapPoint): Any {
        return getDataAt(
            x = point.x,
            y = point.y,
        )
    }

    /**
     * 指定した位置の座標を取得する
     * ループしてるかどうかによって補正が異なる
     */
    fun getMapPoint(x: Int, y: Int): MapPoint {
        return if (!isLoop) {
            MapPoint(
                x = x,
                y = y,
            )
        } else {
            MapPoint(
                x = collectX(x = x),
                y = collectY(y = y),
            )
        }
    }

    /**
     * x軸に対して補正を行う
     */
    private fun collectX(x: Int): Int {
        return collectPoint(
            max = width,
            value = x
        )
    }

    /**
     * y軸に対して補正を行う
     */
    private fun collectY(y: Int): Int {
        return collectPoint(
            max = height,
            value = y
        )
    }

    /**
     * @param max 補正の最大値
     * @param value 補正したい値
     */
    private fun collectPoint(max: Int, value: Int): Int {
        if (value < 0) {
            return value + max
        }
        if (max <= value) {
            return value - max
        }
        return value
    }
}
