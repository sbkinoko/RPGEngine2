package gamescreen.map.domain

import core.domain.mapcell.CellType
import gamescreen.map.domain.collision.Square
import kotlin.random.Random

//fixme 背景画像の情報を持たせる
//宝箱を開けたときにすぐに更新するため
data class BackgroundCell(
    val mapPoint: MapPoint,
    val square: Square,
    val cellType: CellType = CellType.Null,
    // fixme 他の値をvalにしたら修正する
    val rnd: Int = Random.nextInt(),
)


/**
 * 背景マスを移動する関数
 */
fun BackgroundCell.moveDisplayPoint(
    dx: Float = 0f,
    dy: Float = 0f,
): BackgroundCell {
    square.move(
        dx = dx,
        dy = dy,
    )
    return this.copy(
        square = Square(
            x = square.x,
            y = square.y,
            size = square.size
        )
    )
}
