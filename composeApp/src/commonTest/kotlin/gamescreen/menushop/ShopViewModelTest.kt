package gamescreen.menushop

import core.ModuleCore
import gamescreen.menushop.repository.shopmenu.ShopMenuRepository
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

class ShopViewModelTest : KoinTest {
    private val shopViewModel: ShopViewModel by inject()

    private val shopMenuRepository: ShopMenuRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleShop,
                ModuleCore,
            )
        }

        // この画面は基本見えるようになっているはず
        runBlocking {
            shopMenuRepository.setVisibility(true)
            delay(50)
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun checkInit() {
        runBlocking {
            var result = false
            var count = 0
            val collectJob = launch {
                shopMenuRepository.isVisibleStateFlow.collect {
                    result = it
                    count++
                }
            }

            delay(50)

            assertEquals(
                expected = true,
                actual = result,
            )

            assertEquals(
                expected = 1,
                actual = count,
            )

            collectJob.cancel()
        }
    }

    @Test
    fun hideMenu() {
        runBlocking {
            var result = false
            var count = 0
            val collectJob = launch {
                shopMenuRepository.isVisibleStateFlow.collect {
                    result = it
                    count++
                }
            }

            delay(50)

            shopViewModel.hideMenu()

            delay(50)

            assertEquals(
                expected = false,
                actual = result,
            )

            assertEquals(
                expected = 2,
                actual = count,
            )

            collectJob.cancel()
        }
    }
}
