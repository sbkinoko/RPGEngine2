package gamescreen.map.repository.collision

import core.domain.mapcell.CellType
import gamescreen.map.ModuleMap
import gamescreen.map.domain.collision.square.NormalRectangle
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CollisionRepositoryTest : KoinTest {
    private val collisionRepository: CollisionRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleMap,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    // 当たり判定を含まないマスであることを確認
    @Test
    fun getData_0() {
        collisionRepository.collisionData(
            cellType = CellType.Road,
            rectangle = NormalRectangle(
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


    // 当たり判定を含むマスであることを確認
    @Test
    fun getData_1() {
        collisionRepository.collisionData(
            cellType = CellType.Water,
            rectangle = NormalRectangle(
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
