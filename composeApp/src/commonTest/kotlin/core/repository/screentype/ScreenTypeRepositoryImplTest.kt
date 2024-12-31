package core.repository.screentype

import core.domain.ScreenType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import main.ModuleMain
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
                ModuleMain,
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
            lateinit var result: ScreenType
            val collectJob = launch {
                screenTypeRepository.screenStateFlow.collect {
                    count++
                    result = it
                }
            }

            screenTypeRepository.setScreenType(
                screenType = ScreenType.BATTLE,
            )

            delay(100)

            assertEquals(
                expected = ScreenType.BATTLE,
                actual = result,
            )

            assertEquals(
                expected = 1,
                actual = count,
            )

            collectJob.cancel()
        }
    }
}
