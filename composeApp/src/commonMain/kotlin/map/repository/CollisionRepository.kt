package map.repository

import map.domain.collision.CollisionDetectShape
import map.domain.collision.Square

class CollisionRepository {

    fun getCollisionDate(
        id: Int,
        cellSize: Float,
        square: Square,
    ): List<CollisionDetectShape> {
        return if (id == 2) {
            listOf(
                Square(
                    x = square.x,
                    y = square.y,
                    size = cellSize
                )
            )
        } else {
            emptyList()
        }
    }
}
