package core.repository.player

import core.ModuleCore
import core.domain.status.PlayerStatus
import core.domain.status.job.Job
import core.domain.status.param.EXP
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

class PlayerCharacterRepositoryImplTest : KoinTest {
    private val playerStatusRepository: core.repository.memory.character.player.PlayerCharacterRepository by inject()

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
                skillList = listOf(),
                toolList = listOf(),
                exp = EXP(
                    EXP.type1,
                ),
                job = Job.Warrior,
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
