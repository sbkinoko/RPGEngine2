package gamescreen.map.domain

// fixme 表示用のクラスであることがわかる名前にする
/**
 * 画面の表示状の座標
 */
data class Point(
    val x: Float = 0f,
    val y: Float = 0f,
)

fun Point.move(
    dx: Float,
    dy: Float,
): Point {
    return this.copy(
        x = x + dx,
        y = y + dy
    )
}
