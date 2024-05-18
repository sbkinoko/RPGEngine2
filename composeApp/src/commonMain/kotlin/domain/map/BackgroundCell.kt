package domain.map

class BackgroundCell {
    var displayPoint: Point = Point()
    var mapPoint: MapPoint = MapPoint()
    var cellSize: Float = 0f

    val leftSide: Float
        get() = displayPoint.x

    val rightSide: Float
        get() = displayPoint.x + cellSize

    val topSide: Float
        get() = displayPoint.y

    val bottomSide: Float
        get() = displayPoint.y + cellSize

    fun moveDisplayPoint(
        dx: Float = 0f,
        dy: Float = 0f,
    ) {
        displayPoint = Point(
            x = displayPoint.x + dx,
            y = displayPoint.y + dy,
        )
    }
}
