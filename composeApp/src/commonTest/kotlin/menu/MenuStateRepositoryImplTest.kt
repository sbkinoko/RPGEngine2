package menu

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import menu.domain.MenuType
import menu.repository.menustate.MenuStateRepository
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MenuStateRepositoryImplTest : KoinTest {
    private val menuStateRepository: MenuStateRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                MenuModule,
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
                menuStateRepository.menuTypeFlow.collect {
                    count++
                }
            }

            menuStateRepository.menuType = MenuType.Status

            delay(100)

            assertEquals(
                expected = MenuType.Status,
                actual = menuStateRepository.menuType
            )
            assertEquals(
                expected = 1,
                actual = count
            )

            collectJob.cancel()
        }
    }
}
