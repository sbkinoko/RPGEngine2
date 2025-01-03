package gamescreen.map.domain

import core.domain.mapcell.CellType
import gamescreen.map.domain.collision.CollisionDetectShape
import gamescreen.map.domain.collision.Square

//fixme 背景画像の情報を持たせる
//宝箱を開けたときにすぐに更新するため
class BackgroundCell(
    cellSize: Float,
    x: Float,
    y: Float,
) {
    var mapPoint: MapPoint = MapPoint()

    val square: Square = Square(
        displayPoint = Point(
            x = x,
            y = y,
        ),
        size = cellSize,
    )

    var collisionList: List<CollisionDetectShape> = emptyList()

    var cellType: CellType = CellType.Null

    var isPlayerIncludeCell = false

    val cellSize: Float
        get() {
            return square.size
        }

    fun moveDisplayPoint(
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
}
