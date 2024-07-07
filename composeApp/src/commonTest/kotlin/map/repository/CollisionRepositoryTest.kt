package map.repository

import map.domain.collision.Square
import kotlin.test.Test
import kotlin.test.assertEquals

class CollisionRepositoryTest {
    private val collisionRepository = CollisionRepository()

    @Test
    fun getData_0() {
        collisionRepository.getCollisionData(
            id = 0,
            cellSize = 10f,
            square = Square(
                x = 0f,
                y = 0f,
                size = 10f
            )
        ).apply {
            assertEquals(
                expected = 0,
                actual = size,
            )
        }
    }

    @Test
    fun getData_1() {
        collisionRepository.getCollisionData(
            id = 2,
            cellSize = 10f,
            square = Square(
                x = 0f,
                y = 0f,
                size = 10f
            )
        ).apply {
            assertEquals(
                expected = 1,
                actual = size,
            )
        }
    }
}
