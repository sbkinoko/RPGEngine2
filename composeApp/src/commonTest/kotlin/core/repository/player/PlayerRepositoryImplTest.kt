package core.repository.player

import core.CoreModule
import core.domain.status.PlayerStatus
import core.domain.status.param.HP
import core.domain.status.param.MP
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

class PlayerStatusRepositoryImplTest : KoinTest {
    private val playerStatusRepository: PlayerStatusRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                CoreModule
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
                playerStatusRepository.mutablePlayersFlow.collect {
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
                ),
                skillList = listOf(),
                toolList = listOf(),
            )

            playerStatusRepository.setStatus(
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
                actual = playerStatusRepository.getStatus(id),
            )

            collectJob.cancel()
        }
    }
}
