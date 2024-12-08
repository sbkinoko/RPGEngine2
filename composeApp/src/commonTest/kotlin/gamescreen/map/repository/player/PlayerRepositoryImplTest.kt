package gamescreen.map.repository.player

import gamescreen.map.ModuleMap
import gamescreen.map.domain.Point
import gamescreen.map.domain.collision.Square
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    @OptIn(DelicateCoroutinesApi::class)
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
            var count = 0
            val collectJob: Job = launch {
                repository.playerPositionFLow.asSharedFlow()
                    .onEach {
                        count++
                    }.launchIn(GlobalScope)
            }

            repository.setPlayerPosition(
                square = square
            )
            delay(100)

            val a = repository.playerPositionFLow.first()
            assertEquals(
                expected = square,
                actual = a,
            )
            assertEquals(
                expected = 1,
                actual = count
            )

            repository.reload()
            delay(100)

            assertEquals(
                expected = square,
                actual = a,
            )
            assertEquals(
                expected = 2,
                actual = count
            )

            collectJob.cancel()
        }
    }
}
