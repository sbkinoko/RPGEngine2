package map.repository.player

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import map.MapModule
import map.domain.Point
import map.domain.collision.Square
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PlayerRepositoryImplTest : KoinTest {

    private val repository: PlayerRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                MapModule
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun setPlayerPositionTest() {
        val square = Square(
            displayPoint = Point(
                x = 0f,
                y = 0f
            ),
            size = 10f,
        )
        runBlocking {
            repository.setPlayerPosition(
                square = square
            )

            repository.getPlayerPosition().apply {
                assertEquals(
                    expected = square,
                    actual = this,
                )
            }
        }
    }

    @Test
    fun checkFlow() {
        val square = Square(
            displayPoint = Point(
                x = 0f,
                y = 0f
            ),
            size = 10f,
        )

        runBlocking {
            try {
                repository.playerPositionFLow.first()
                assertTrue { false }
            } catch (e: Exception) {
                // 値がないことを確認
                assertTrue { true }
            }
            repository.setPlayerPosition(
                square = square
            )
            repository.playerPositionFLow.first().apply {
                assertEquals(
                    expected = square,
                    actual = this,
                )
            }
        }
    }
}
