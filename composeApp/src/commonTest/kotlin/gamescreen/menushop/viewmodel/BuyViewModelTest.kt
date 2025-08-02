package gamescreen.menushop.viewmodel

import core.ModuleCore
import gamescreen.choice.ModuleChoice
import gamescreen.menu.ModuleMenu
import gamescreen.menushop.ModuleShop
import gamescreen.menushop.domain.ShopType
import gamescreen.menushop.domain.amountdata.dummyItem
import gamescreen.menushop.repository.shopmenu.ShopMenuRepository
import gamescreen.text.ModuleText
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

class BuyViewModelTest : KoinTest {
    private val shopViewModel: BuyViewModel by inject()

    private val shopMenuRepository: ShopMenuRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleShop,
                ModuleCore,
                ModuleText,
                ModuleChoice,
                ModuleMenu,
            )
        }

        // この画面は基本見えるようになっているはず
        runBlocking {
            shopMenuRepository.setList(
                listOf(dummyItem)
            )
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
            var result = ShopType.CLOSE
            var count = 0
            val collectJob = launch {
                shopMenuRepository.shopTypeStateFlow.collect {
                    result = it
                    count++
                }
            }

            delay(50)

            assertEquals(
                expected = ShopType.BUY,
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
            var result = ShopType.CLOSE
            var count = 0
            val collectJob = launch {
                shopMenuRepository.shopTypeStateFlow.collect {
                    result = it
                    count++
                }
            }

            delay(50)

            shopViewModel.hideMenu()

            delay(50)

            assertEquals(
                expected = ShopType.CLOSE,
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
