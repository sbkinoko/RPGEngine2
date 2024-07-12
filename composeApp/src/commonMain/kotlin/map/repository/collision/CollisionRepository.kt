package map.repository.collision

import map.domain.collision.CollisionDetectShape
import map.domain.collision.Square

interface CollisionRepository {
    fun collisionData(
        id: Int,
        cellSize: Float,
        square: Square,
    ): List<CollisionDetectShape>
}
