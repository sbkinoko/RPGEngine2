package common.repository.player

import common.CommonModule
import common.status.PlayerStatus
import common.status.param.HP
import common.status.param.MP
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

class PlayerRepositoryImplTest : KoinTest {
    private val playerRepository: PlayerRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                CommonModule
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun setAndGet() {
        var count = 0
        runBlocking {
            val collectJob = launch {
                playerRepository.mutablePlayersFlow.collect {
                    count++
                }
            }

            val id = 0
            val playerStatus = PlayerStatus(
                name = "test",
                hp = HP(
                    maxValue = 100,
                    value = 50,
                ),
                mp = MP(
                    maxValue = 10,
                    value = 5,
                )
            )

            playerRepository.setStatus(
                id = id,
                status = playerStatus,
            )

            delay(100)

            assertEquals(
                expected = 1,
                actual = count,
            )

            assertEquals(
                expected = playerStatus,
                actual = playerRepository.getStatus(id),
            )

            collectJob.cancel()
        }
    }
}
