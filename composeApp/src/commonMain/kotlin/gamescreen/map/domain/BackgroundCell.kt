package gamescreen.map.domain

import core.domain.mapcell.CellType
import gamescreen.map.domain.collision.CollisionDetectShape
import gamescreen.map.domain.collision.Square
import kotlin.random.Random

//fixme 背景画像の情報を持たせる
//宝箱を開けたときにすぐに更新するため
data class BackgroundCell(
    // fixme squareに依存する形に修正する
    val cellSize: Float,
    val x: Float,
    val y: Float,
    val mapPoint: MapPoint,
    // fixme cellTypeとsquareに完全に依存しているので不要
    val collisionList: List<CollisionDetectShape> = emptyList(),
    val square: Square = Square(
        point = Point(
            x = x,
            y = y,
        ),
        size = cellSize,
    ),
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
) {
    square.move(
        dx = dx,
        dy = dy,
    )

    collisionList.forEach {
        (it as Square).move(
            dx = dx,
            dy = dy,
        )
    }
}
