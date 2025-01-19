package gamescreen.menushop.amountdata

import gamescreen.menushop.ModuleShop
import kotlinx.coroutines.delay
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
            actual = amountData.num,
        )
    }

    @Test
    fun checkDigit1() {
        runBlocking {
            amountData.set(43)
            delay(5)

            assertEquals(
                expected = 4,
                actual = amountData.amount2.value
            )

            assertEquals(
                expected = 3,
                actual = amountData.amount1.value
            )
        }
    }

    @Test
    fun checkDigit2() {
        runBlocking {
            amountData.set(51)
            delay(5)

            assertEquals(
                expected = 5,
                actual = amountData.amount2.value
            )

            assertEquals(
                expected = 1,
                actual = amountData.amount1.value
            )
        }
    }

    /**
     * 1桁目の加算の確認
     */
    @Test
    fun inc1_1() {
        runBlocking {
            amountData.incAmount1()

            delay(50)

            assertEquals(
                expected = 1,
                actual = amountData.num
            )
        }
    }

    /**
     * 1桁目の減算の確認
     */
    @Test
    fun dec1_1() {
        runBlocking {

            amountData.set(5)
            delay(5)

            amountData.decAmount1()
            delay(50)

            assertEquals(
                expected = 4,
                actual = amountData.num
            )
        }
    }

    /**
     * 2桁目の加算の確認
     */
    @Test
    fun inc2_1() {
        runBlocking {
            amountData.incAmount2()

            delay(50)

            assertEquals(
                expected = 10,
                actual = amountData.num
            )
        }
    }

    /**
     * 2桁目の加算の確認
     */
    @Test
    fun dec2_1() {
        runBlocking {
            amountData.set(21)
            delay(5)

            amountData.decAmount2()

            delay(50)

            assertEquals(
                expected = 11,
                actual = amountData.num
            )
        }
    }

    /**
     * 1桁目の繰り上がりの確認
     */
    @Test
    fun inc1_10() {
        runBlocking {
            amountData.set(9)
            delay(5)

            amountData.incAmount1()
            delay(5)

            assertEquals(
                expected = 10,
                actual = amountData.num,
            )
        }
    }

    /**
     * 最大個数未満の超過
     */
    @Test
    fun over99() {
        runBlocking {

            amountData.set(95)
            delay(5)

            amountData.incAmount2()
            delay(5)

            assertEquals(
                expected = 99,
                actual = amountData.num,
            )
        }
    }

    /**
     * 最大個数未満の超過
     */
    @Test
    fun overMaxNum() {
        runBlocking {
            amountData.maxNum = 50

            amountData.set(49)
            amountData.incAmount2()

            delay(5)

            assertEquals(
                expected = 50,
                actual = amountData.num,
            )
        }
    }

    /**
     * 最大個数での超過
     */
    @Test
    fun inc99_1() {
        runBlocking {
            amountData.set(99)
            delay(5)

            amountData.incAmount1()
            delay(5)

            assertEquals(
                expected = 0,
                actual = amountData.num,
            )
        }
    }

    /**
     * 最大個数での超過
     */
    @Test
    fun inc99_2() {
        runBlocking {

            amountData.set(99)
            delay(5)

            amountData.incAmount2()

            delay(5)

            assertEquals(
                expected = 0,
                actual = amountData.num,
            )
        }
    }

    /**
     * 最小個数での減算
     */
    @Test
    fun dec0_1() {
        runBlocking {
            amountData.decAmount1()
            delay(5)

            assertEquals(
                expected = 99,
                actual = amountData.num,
            )
        }
    }

    /**
     * 最小個数での減算
     */
    @Test
    fun dec0_2() {
        runBlocking {
            amountData.decAmount2()
            delay(5)

            assertEquals(
                expected = 99,
                actual = amountData.num,
            )
        }
    }

    /**
     * 最小個数での減算
     */
    @Test
    fun decTo0() {
        runBlocking {
            amountData.set(5)

            amountData.decAmount2()
            delay(5)

            assertEquals(
                expected = 0,
                actual = amountData.num,
            )
        }
    }
}
