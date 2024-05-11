package domain.map

class BackgroundCell {
    var displayPoint: Point = Point()
    var mapPoint: MapPoint = MapPoint()

    fun moveDisplayPoint(
        dx: Float,
        dy: Float,
    ) {
        displayPoint = Point(
            x = displayPoint.x + dx,
            y = displayPoint.y + dy,
        )
    }
}
