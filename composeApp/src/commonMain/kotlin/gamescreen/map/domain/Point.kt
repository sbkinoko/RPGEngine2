package gamescreen.map.domain

class Point(
    var x: Float = 0f,
    var y: Float = 0f,
) {
    fun move(
        dx: Float,
        dy: Float,
    ) {
        x += dx
        y += dy
    }
}
