package gamescreen.menushop.amountdata

import gamescreen.menushop.ModuleShop
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

class AmountDataImplTest : KoinTest {
    private val amountData: AmountData by inject()

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
    fun initTest() {
        assertEquals(
            expected = 0,
            actual = amountData.amount1.value,
        )


        assertEquals(
            expected = 0,
            actual = amountData.amount2.value,
        )

        assertEquals(
            expected = 0,
            actual = amountData.amount3.value,
        )
    }

    /**
     * 1桁目の加算の確認
     */
    @Test
    fun inc1_1() {
        runBlocking {
            var result = 0
            val collectJob = launch {
                amountData.amount1.collect {
                    result = it
                }
            }

            amountData.incAmount1()

            delay(50)

            assertEquals(
                expected = 1,
                actual = result
            )

            collectJob.cancel()
        }
    }

    /**
     * 2桁目の加算の確認
     */
    @Test
    fun inc2_1() {
        runBlocking {
            var result = 0
            val collectJob = launch {
                amountData.amount2.collect {
                    result = it
                }
            }

            amountData.incAmount2()

            delay(50)

            assertEquals(
                expected = 1,
                actual = result
            )

            collectJob.cancel()
        }
    }

    /**
     * 1桁目の繰り上がりの確認
     */
    @Test
    fun inc1_10() {
        runBlocking {
            var result1 = 0
            val collectJob1 = launch {
                amountData.amount1.collect {
                    result1 = it
                }
            }

            var result2 = 0
            val collectJob2 = launch {
                amountData.amount2.collect {
                    result2 = it
                }
            }

            repeat(10) {
                amountData.incAmount1()
                delay(50)
            }

            assertEquals(
                expected = 0,
                actual = result1
            )

            assertEquals(
                expected = 1,
                actual = result2,
            )

            collectJob1.cancel()
            collectJob2.cancel()
        }
    }


    /**
     * 最大個数未満の超過
     */
    @Test
    fun over99() {
        runBlocking {
            var result1 = 0
            val collectJob1 = launch {
                amountData.amount1.collect {
                    result1 = it
                }
            }

            var result2 = 0
            val collectJob2 = launch {
                amountData.amount2.collect {
                    result2 = it
                }
            }

            repeat(95) {
                amountData.incAmount1()
                delay(5)
            }

            assertEquals(
                expected = 5,
                actual = result1
            )

            assertEquals(
                expected = 9,
                actual = result2,
            )

            amountData.incAmount2()

            delay(5)

            assertEquals(
                expected = 9,
                actual = result1
            )

            assertEquals(
                expected = 9,
                actual = result2,
            )

            collectJob1.cancel()
            collectJob2.cancel()
        }
    }

    /**
     * 最大個数未満の超過
     */
    @Test
    fun overMaxNum() {
        runBlocking {
            amountData.maxNum = 50

            var result1 = 0
            val collectJob1 = launch {
                amountData.amount1.collect {
                    result1 = it
                }
            }

            var result2 = 0
            val collectJob2 = launch {
                amountData.amount2.collect {
                    result2 = it
                }
            }

            repeat(49) {
                amountData.incAmount1()
                delay(5)
            }

            assertEquals(
                expected = 9,
                actual = result1
            )

            assertEquals(
                expected = 4,
                actual = result2,
            )

            amountData.incAmount2()

            delay(5)

            assertEquals(
                expected = 0,
                actual = result1
            )

            assertEquals(
                expected = 5,
                actual = result2,
            )

            collectJob1.cancel()
            collectJob2.cancel()
        }
    }

    /**
     * 最大個数での超過
     */
    @Test
    fun inc99_1() {
        runBlocking {
            var result1 = 0
            val collectJob1 = launch {
                amountData.amount1.collect {
                    result1 = it
                }
            }

            var result2 = 0
            val collectJob2 = launch {
                amountData.amount2.collect {
                    result2 = it
                }
            }

            repeat(99) {
                amountData.incAmount1()
                delay(5)
            }

            assertEquals(
                expected = 9,
                actual = result1
            )

            assertEquals(
                expected = 9,
                actual = result2,
            )

            amountData.incAmount1()

            delay(5)

            assertEquals(
                expected = 0,
                actual = result1
            )

            assertEquals(
                expected = 0,
                actual = result2,
            )

            collectJob1.cancel()
            collectJob2.cancel()
        }
    }

    /**
     * 最大個数での超過
     */
    @Test
    fun inc99_2() {
        runBlocking {
            var result1 = 0
            val collectJob1 = launch {
                amountData.amount1.collect {
                    result1 = it
                }
            }

            var result2 = 0
            val collectJob2 = launch {
                amountData.amount2.collect {
                    result2 = it
                }
            }

            repeat(99) {
                amountData.incAmount1()
                delay(5)
            }

            assertEquals(
                expected = 9,
                actual = result1
            )

            assertEquals(
                expected = 9,
                actual = result2,
            )

            amountData.incAmount2()

            delay(5)

            assertEquals(
                expected = 0,
                actual = result1
            )

            assertEquals(
                expected = 0,
                actual = result2,
            )

            collectJob1.cancel()
            collectJob2.cancel()
        }
    }
}
