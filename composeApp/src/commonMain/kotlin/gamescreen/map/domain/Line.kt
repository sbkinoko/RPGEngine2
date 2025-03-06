package gamescreen.map.domain

data class Line(
    val point1: Point,
    val point2: Point,
) {
    val isVertical = point1.x == point2.x
    val isHorizontal = point1.y == point2.y
}
