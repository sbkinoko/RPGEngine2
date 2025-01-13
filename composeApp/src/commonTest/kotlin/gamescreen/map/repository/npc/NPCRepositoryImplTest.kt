package gamescreen.map.repository.npc

import gamescreen.map.ModuleMap
import gamescreen.map.domain.collision.EventSquare
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import values.EventType
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NPCRepositoryImplTest : KoinTest {
    private val npcRepository: NPCRepository by inject()

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

    @Test
    fun initialTest() {
        runBlocking {
            var count = 0
            lateinit var result: List<EventSquare>
            val collectJob = launch {
                npcRepository.npcStateFlow.collect {
                    count++
                    result = it
                }
            }

            delay(50)

            assertEquals(
                expected = listOf(),
                actual = result
            )

            assertEquals(
                expected = 1,
                actual = count,
            )

            collectJob.cancel()
        }
    }

    @Test
    fun checkFlow() {
        val eventSquare = EventSquare(
            x = 1f,
            y = 2f,
            size = 3f,
            eventID = EventType.Talk
        )
        val list = listOf(eventSquare)

        runBlocking {
            var count = 0
            lateinit var result: List<EventSquare>
            val collectJob = launch {
                npcRepository.npcStateFlow.collect {
                    count++
                    result = it
                }
            }

            delay(50)

            npcRepository.setNpc(
                list,
            )

            delay(50)


            assertEquals(
                expected = list,
                actual = result
            )

            assertEquals(
                expected = 2,
                actual = count,
            )

            collectJob.cancel()
        }
    }
}
