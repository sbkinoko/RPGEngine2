package map.repository

import map.domain.collision.Square
import kotlin.test.Test

class CollisionRepositoryTest {
    private val collisionRepository = CollisionRepository()

    @Test
    fun getDataTest() {
        // fixme 正しいテストを作る
        collisionRepository.getCollisionData(
            id = 0,
            cellSize = 10f,
            square = Square(
                x = 0f,
                y = 0f,
                size = 10f
            )
        )
    }
}
