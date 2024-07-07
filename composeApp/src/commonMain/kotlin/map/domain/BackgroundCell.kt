package map.domain

import map.domain.collision.CollisionDetectShape
import map.domain.collision.Square
import map.repository.CollisionRepository

class BackgroundCell(
    cellSize: Float,
    x: Float,
    y: Float,
) {
    // FIXME: privateにする
    var mapPoint: MapPoint = MapPoint()

    val square: Square = Square(
        displayPoint = Point(
            x = x,
            y = y,
        ),
        size = cellSize,
    )

    var collisionList: List<CollisionDetectShape> = emptyList()
    private val collisionRepository = CollisionRepository()

    var imgID: Int = 0
        set(value) {
            field = value
            collisionList = collisionRepository.getCollisionData(
                cellSize = cellSize,
                square = square,
                id = value,
            )
        }

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
