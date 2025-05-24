package core.repository.player

import core.ModuleCore
import core.domain.status.PlayerStatus
import core.domain.status.StatusData
import core.domain.status.param.EXP
import core.domain.status.param.StatusParameterWithMax
import data.ModuleData
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
                ModuleCore,
                ModuleData,
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
                playerStatusRepository.playerStatusFlow.collect {
                    count++
                }
            }

            val id = 0
            val playerStatus = PlayerStatus(
                statusData = StatusData(
                    name = "test",
                    hp = StatusParameterWithMax(
                        maxPoint = 100,
                        point = 50,
                    ),
                    mp = StatusParameterWithMax(
                        maxPoint = 10,
                        point = 5,
                    ),
                ),
                skillList = listOf(),
                toolList = listOf(),
                exp = EXP(
                    EXP.type1,
                ),
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
