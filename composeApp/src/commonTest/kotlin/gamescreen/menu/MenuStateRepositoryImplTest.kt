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
                menuStateRepository.commandTypeFlow.collect {
                    count++
                }
            }

            menuStateRepository.push(MenuType.Status)

            delay(100)

            assertEquals(
                expected = MenuType.Status,
                actual = menuStateRepository.nowCommandType
            )
            assertEquals(
                expected = 1,
                actual = count
            )

            menuStateRepository.pop()

            delay(100)
            assertEquals(
                expected = MenuType.Main,
                actual = menuStateRepository.nowCommandType
            )
            assertEquals(
                expected = 2,
                actual = count
            )

            collectJob.cancel()
        }
    }

    @Test
    fun checkReset() {
        var count = 0
        runBlocking {
            val collectJob = launch {
                menuStateRepository.commandTypeFlow.collect {
                    count++
                }
            }

            menuStateRepository.push(MenuType.Status)
            delay(100)

            menuStateRepository.push(MenuType.SKILL_USER)
            delay(100)

            menuStateRepository.reset()

            delay(100)

            //　リセットしたので標準状態
            assertEquals(
                expected = MenuType.Main,
                actual = menuStateRepository.nowCommandType
            )
            // セット2回とリセット分
            assertEquals(
                expected = 3,
                actual = count
            )

            // popできないことを確認
            menuStateRepository.pop()
            delay(100)

            assertEquals(
                expected = MenuType.Main,
                actual = menuStateRepository.nowCommandType
            )
            assertEquals(
                expected = 3,
                actual = count
            )

            collectJob.cancel()
        }
    }
}
