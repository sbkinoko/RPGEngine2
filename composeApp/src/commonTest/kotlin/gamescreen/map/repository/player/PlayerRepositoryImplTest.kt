package gamescreen.map.repository.player

import gamescreen.map.ModuleMap
import gamescreen.map.domain.Player
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

    private val player = Player(
        size = 10F
    )

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
        runBlocking {
            repository.setPlayerPosition(
                player = player
            )

            repository.getPlayerPosition().apply {
                assertEquals(
                    expected = player,
                    actual = this,
                )
            }
        }
    }

    @Test
    fun checkFlow() {
        runBlocking {
            var count = 0
            lateinit var result: Player
            val collectJob: Job = launch {
                repository.playerPositionStateFlow.collect {
                    count++
                    result = it
                }
            }

            repository.setPlayerPosition(
                player = player,
            )
            delay(100)

            assertEquals(
                expected = player,
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
