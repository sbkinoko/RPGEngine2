package gamescreen.map.domain.background

import core.domain.mapcell.CellType
import gamescreen.map.domain.MapPoint
import gamescreen.map.domain.collision.square.Rectangle
import gamescreen.map.domain.collision.square.RectangleWrapper

//fixme 背景画像の情報を持たせる
//宝箱を開けたときにすぐに更新するため
data class BackgroundCell(
    val mapPoint: MapPoint,
    override val rectangle: Rectangle,
    val cellType: CellType = CellType.Null,

    // fixme デフォルト値削除
    val aroundCellId: List<List<CellType>> = emptyList(),
) : RectangleWrapper<BackgroundCell>(
    rectangle = rectangle,
) {
    override fun move(dx: Float, dy: Float): BackgroundCell {
        return this.copy(
            rectangle = rectangle.move(
                dx = dx,
                dy = dy,
            )
        )
    }

    override fun moveTo(x: Float, y: Float): BackgroundCell {
        return this.copy(
            rectangle = rectangle.moveTo(
                x = x,
                y = y,
            )
        )
    }
}
