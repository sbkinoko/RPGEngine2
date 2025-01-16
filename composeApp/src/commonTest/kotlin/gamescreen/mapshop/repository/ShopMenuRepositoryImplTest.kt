package gamescreen.mapshop.repository

import gamescreen.mapshop.ModuleShop
import gamescreen.mapshop.repoisitory.ShopMenuRepository
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
    fun checkInit() {
        runBlocking {
            var result = false
            var count = 0
            val collectJob = launch {
                shopMenuRepository.isVisibleStateFlow
                    .collect {
                        result = it
                        count++
                    }
            }

            delay(50)

            assertEquals(
                expected = false,
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
    fun setTrueAndFalse() {
        runBlocking {
            var result = false
            var count = 0
            val collectJob = launch {
                shopMenuRepository.isVisibleStateFlow
                    .collect {
                        result = it
                        count++
                    }
            }

            delay(50)

            val first = true
            shopMenuRepository.setVisibility(
                isVisible = first,
            )

            delay(50)

            assertEquals(
                expected = first,
                actual = result,
            )

            assertEquals(
                expected = 2,
                actual = count,
            )

            val second = false
            shopMenuRepository.setVisibility(
                isVisible = second,
            )

            delay(50)

            assertEquals(
                expected = second,
                actual = result,
            )

            assertEquals(
                expected = 3,
                actual = count,
            )

            collectJob.cancel()
        }
    }
}
