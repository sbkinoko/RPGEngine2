package main.repository.screentype

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import main.MainModule
import main.domain.ScreenType
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ScreenTypeRepositoryImplTest : KoinTest {
    private val screenTypeRepository: ScreenTypeRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                MainModule,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun checkSet() {
        var count = 0
        runBlocking {
            val collectJob = launch {
                screenTypeRepository.screenTypeFlow.collect {
                    count++
                }
            }

            screenTypeRepository.screenType = ScreenType.BATTLE
            assertEquals(
                expected = ScreenType.BATTLE,
                actual = screenTypeRepository.screenType
            )

            delay(100)
            assertEquals(
                expected = 1,
                actual = count
            )
            collectJob.cancel()
        }
    }
}
