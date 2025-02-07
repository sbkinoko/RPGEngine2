package gamescreen.map.domain.background

import core.domain.mapcell.CellType
import gamescreen.map.domain.MapPoint
import gamescreen.map.domain.collision.square.Square
import gamescreen.map.domain.collision.square.SquareWrapper

//fixme 背景画像の情報を持たせる
//宝箱を開けたときにすぐに更新するため
data class BackgroundCell(
    val mapPoint: MapPoint,
    override val square: Square,
    val cellType: CellType = CellType.Null,
) : SquareWrapper<BackgroundCell>(
    square = square,
) {
    override fun move(dx: Float, dy: Float): BackgroundCell {
        return this.copy(
            square = square.move(
                dx = dx,
                dy = dy,
            )
        )
    }

    override fun moveTo(x: Float, y: Float): BackgroundCell {
        return this.copy(
            square = square.moveTo(
                x = x,
                y = y,
            )
        )
    }
}
