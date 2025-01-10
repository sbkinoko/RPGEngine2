package gamescreen.map.domain

// fixme data class にしたい
// fixme 表示用のクラスであることがわかる名前にする
/**
 * 画面の表示状の座標
 */
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
