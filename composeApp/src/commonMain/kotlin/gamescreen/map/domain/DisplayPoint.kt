package gamescreen.map.domain

// fixme data class にしたい
/**
 * 画面の表示状の座標
 */
class DisplayPoint(
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
