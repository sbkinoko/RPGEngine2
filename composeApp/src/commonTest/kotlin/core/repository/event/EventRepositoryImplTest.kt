package core.repository.event

import core.ModuleCore
import core.domain.BattleResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

class EventRepositoryImplTest : KoinTest {
    private val eventRepository by inject<EventRepository>()

    private var winCount = 0
    private val winCallBack = {
        winCount++
        Unit
    }

    private var loseCount = 0
    private val loseCallback = {
        loseCount++
        Unit
    }

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleCore,
            )
        }
        eventRepository.setCallBack(
            winEvent = winCallBack,
            loseEvent = loseCallback,
        )
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun checkInit() {
        runBlocking {
            lateinit var result: BattleResult
            val collectJob = CoroutineScope(Dispatchers.Default).launch {
                eventRepository.resultStateFlow.collect {
                    result = it
                }
            }

            delay(50)

            assertEquals(
                expected = BattleResult.None,
                actual = result,
            )

            collectJob.cancel()
        }
    }

    @Test
    fun checkWin() {
        runBlocking {
            lateinit var result: BattleResult
            val battleResult = BattleResult.Win
            val collectJob = CoroutineScope(Dispatchers.Default).launch {
                eventRepository.resultStateFlow.collect {
                    result = it
                }
            }

            delay(50)

            eventRepository.setResult(
                battleResult,
            )

            delay(50)

            assertEquals(
                expected = battleResult,
                actual = result,
            )

            assertEquals(
                expected = 1,
                actual = winCount,
            )

            assertEquals(
                expected = 0,
                actual = loseCount,
            )

            collectJob.cancel()
        }
    }

    @Test
    fun checkLose() {
        runBlocking {
            lateinit var result: BattleResult
            val battleResult = BattleResult.Lose
            val collectJob = CoroutineScope(Dispatchers.Default).launch {
                eventRepository.resultStateFlow.collect {
                    result = it
                }
            }

            delay(50)

            eventRepository.setResult(
                battleResult,
            )

            delay(50)

            assertEquals(
                expected = battleResult,
                actual = result,
            )

            assertEquals(
                expected = 0,
                actual = winCount,
            )

            assertEquals(
                expected = 1,
                actual = loseCount,
            )

            collectJob.cancel()
        }
    }

    @Test
    fun checkNone() {
        runBlocking {
            lateinit var result: BattleResult
            val battleResult = BattleResult.None
            val collectJob = CoroutineScope(Dispatchers.Default).launch {
                eventRepository.resultStateFlow.collect {
                    result = it
                }
            }

            delay(50)

            eventRepository.setResult(
                battleResult,
            )

            delay(50)

            assertEquals(
                expected = battleResult,
                actual = result,
            )

            assertEquals(
                expected = 0,
                actual = winCount,
            )

            assertEquals(
                expected = 0,
                actual = loseCount,
            )

            collectJob.cancel()
        }
    }
}
