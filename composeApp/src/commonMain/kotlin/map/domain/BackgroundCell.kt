package map.domain

class BackgroundCell(
    cellSize: Float,
    x: Float,
    y: Float,
) {
    // FIXME: privateにする
    var mapPoint: MapPoint = MapPoint()

    val square: Square

    var imgID: Int = 0

    var isPlayerIncludeCell = false

    init {
        square = Square(
            displayPoint = Point(
                x = x,
                y = y,
            ),
            size = cellSize,
        )
    }

    val cellSize: Float
        get() {
            return square.size
        }

    fun moveDisplayPoint(
        dx: Float = 0f,
        dy: Float = 0f,
    ) {
        square.move(
            dx = dx,
            dy = dy,
        )
    }
}
