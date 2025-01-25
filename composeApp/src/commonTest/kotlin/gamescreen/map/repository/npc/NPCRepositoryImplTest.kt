package gamescreen.map.repository.npc

import gamescreen.map.ModuleMap
import gamescreen.map.domain.MapPoint
import gamescreen.map.domain.npc.NPC
import gamescreen.map.domain.npc.NPCType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import values.event.TalkEvent
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
            lateinit var result: List<NPC>
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
        val npc = NPC(
            npcType = NPCType.GIRL,
            mapPoint = MapPoint(0, 0),
            size = 3f,
            eventType = TalkEvent.Talk1
        )

        val list = listOf(npc)

        runBlocking {
            var count = 0
            lateinit var result: List<NPC>
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
