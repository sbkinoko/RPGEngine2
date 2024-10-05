package gamescreen.map.repository.collision

import gamescreen.map.domain.collision.CollisionDetectShape
import gamescreen.map.domain.collision.Square

class CollisionRepositoryImpl : CollisionRepository {

    override fun collisionData(
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
