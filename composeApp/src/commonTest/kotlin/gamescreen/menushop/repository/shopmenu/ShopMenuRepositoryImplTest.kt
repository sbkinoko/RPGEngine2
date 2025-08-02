package gamescreen.menushop.repository.shopmenu

import gamescreen.menushop.ModuleShop
import gamescreen.menushop.domain.ShopItem
import gamescreen.menushop.domain.amountdata.dummyItem
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

class ShopMenuRepositoryImplTest : KoinTest {

    private val shopMenuRepository: ShopMenuRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleShop,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun setTrueAndFalse() {
        runBlocking {
            var result2 = emptyList<ShopItem>()
            val collectJob2 = launch {
                shopMenuRepository.shopItemListStateFlow
                    .collect {
                        result2 = it
                    }
            }

            delay(50)

            val first = listOf(
                dummyItem,
            )
            shopMenuRepository.setList(
                list = first,
            )

            delay(50)

            assertEquals(
                expected = first,
                actual = result2,
            )

            val second = emptyList<ShopItem>()
            shopMenuRepository.setList(
                second,
            )

            delay(50)


            assertEquals(
                expected = second,
                actual = result2,
            )

            collectJob2.cancel()
        }
    }
}
