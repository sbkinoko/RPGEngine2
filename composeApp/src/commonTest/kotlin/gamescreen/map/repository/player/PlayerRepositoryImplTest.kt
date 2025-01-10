package gamescreen.map.repository.player

import gamescreen.map.ModuleMap
import gamescreen.map.domain.DisplayPoint
import gamescreen.map.domain.collision.Square
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerPositionRepositoryImplTest : KoinTest {

    private val repository: PlayerPositionRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleMap
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
            displayPoint = DisplayPoint(
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

    @OptIn(DelicateCoroutinesApi::class)
    @Test
    fun checkFlow() {
        val square = Square(
            displayPoint = DisplayPoint(
                x = 0f,
                y = 0f
            ),
            size = 10f,
        )

        runBlocking {
            var count = 0
            lateinit var result: Square
            val collectJob: Job = launch {
                repository.playerPositionStateFlow.collect {
                    count++
                    result = it
                }
            }

            repository.setPlayerPosition(
                square = square
            )
            delay(100)

            assertEquals(
                expected = square,
                actual = result,
            )
            assertEquals(
                expected = 1,
                actual = count
            )

            collectJob.cancel()
        }
    }
}
