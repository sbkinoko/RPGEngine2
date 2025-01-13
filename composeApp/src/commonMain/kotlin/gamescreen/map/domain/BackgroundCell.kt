package gamescreen.map.domain

import core.domain.mapcell.CellType
import gamescreen.map.domain.collision.square.NormalSquare

//fixme 背景画像の情報を持たせる
//宝箱を開けたときにすぐに更新するため
data class BackgroundCell(
    val mapPoint: MapPoint,
    val square: NormalSquare,
    val cellType: CellType = CellType.Null,
)


/**
 * 背景マスを移動する関数
 */
fun BackgroundCell.moveDisplayPoint(
    dx: Float = 0f,
    dy: Float = 0f,
): BackgroundCell {
    return this.copy(
        square = square.move(
            dx = dx,
            dy = dy,
        )
    )
}
