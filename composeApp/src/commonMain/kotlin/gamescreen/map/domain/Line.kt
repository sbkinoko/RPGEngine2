package gamescreen.map.domain

data class Line(
    val point1: Point,
    val point2: Point,
) {
    val inclination: Inclination = if (point1.x == point2.x) {
        Inclination.Vertical
    } else if (point1.y == point2.y) {
        Inclination.Horizontal
    } else {
        Inclination.Slope(
            (point2.y - point1.y) / (point2.x - point1.x)
        )
    }

    val intercept = (point1.x * point2.y - point1.y * point2.x) / (point1.x - point2.x)
}

sealed class Inclination {
    data object Vertical : Inclination()

    data object Horizontal : Inclination()

    data class Slope(
        val a: Float,
    ) : Inclination()
}
