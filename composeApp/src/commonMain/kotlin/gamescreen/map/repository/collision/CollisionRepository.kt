package gamescreen.map.repository.collision

import gamescreen.map.domain.collision.CollisionDetectShape
import gamescreen.map.domain.collision.Square

interface CollisionRepository {
    fun collisionData(
        id: Int,
        cellSize: Float,
        square: Square,
    ): List<CollisionDetectShape>
}
