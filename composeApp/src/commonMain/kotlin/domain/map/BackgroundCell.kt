package domain.map

class BackgroundCell(
    cellSize: Float,
    x: Float,
    y: Float,
) {
    var mapPoint: MapPoint = MapPoint()

    val square: Square

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
