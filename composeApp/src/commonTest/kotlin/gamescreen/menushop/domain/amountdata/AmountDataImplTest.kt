package gamescreen.menushop.domain.amountdata

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

        amountData.reset()
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    // todo スティック操作のテストを作る
    /**
     * 初期値の確認
     */
    @Test
    fun initTest() {
        assertEquals(
            expected = 0,
            actual = amountData.buttonDataList[DIGIT_10].dataFlow.value,
        )

        assertEquals(
            expected = 1,
            actual = amountData.buttonDataList[DIGIT_1].dataFlow.value,
        )

        assertEquals(
            expected = 1,
            actual = amountData.num,
        )
    }

    /**
     * 各桁の値の確認
     */
    @Test
    fun checkDigit1() {
        runBlocking {
            amountData.set(43)
            delay(5)

            assertEquals(
                expected = 4,
                actual = amountData.buttonDataList[DIGIT_10].dataFlow.value
            )

            assertEquals(
                expected = 3,
                actual = amountData.buttonDataList[DIGIT_1].dataFlow.value
            )
        }
    }

    /**
     * 各桁の値の確認
     */
    @Test
    fun checkDigit2() {
        runBlocking {
            amountData.set(51)
            delay(5)

            assertEquals(
                expected = 5,
                actual = amountData.buttonDataList[DIGIT_10].dataFlow.value
            )

            assertEquals(
                expected = 1,
                actual = amountData.buttonDataList[DIGIT_1].dataFlow.value
            )
        }
    }

    /**
     * 1桁目の加算の確認
     */
    @Test
    fun inc1_1() {
        runBlocking {
            amountData.buttonDataList[DIGIT_1].onClickAdd()

            delay(50)

            assertEquals(
                expected = 2,
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

            amountData.buttonDataList[DIGIT_1].onClickDec()
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
            amountData.buttonDataList[DIGIT_10].onClickAdd()

            delay(50)

            assertEquals(
                expected = 11,
                actual = amountData.num
            )
        }
    }

    /**
     * 2桁目の減算の確認
     */
    @Test
    fun dec2_1() {
        runBlocking {
            amountData.set(21)
            delay(5)

            amountData.buttonDataList[DIGIT_10].onClickDec()

            delay(50)

            assertEquals(
                expected = 11,
                actual = amountData.num
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

            amountData.buttonDataList[DIGIT_10].onClickAdd()
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
            amountData.buttonDataList[DIGIT_1].onClickAdd()

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

            amountData.buttonDataList[DIGIT_1].onClickAdd()
            delay(5)

            assertEquals(
                expected = 1,
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

            amountData.buttonDataList[DIGIT_1].onClickAdd()

            delay(5)

            assertEquals(
                expected = 1,
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
            amountData.buttonDataList[DIGIT_1].onClickDec()
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
            amountData.buttonDataList[DIGIT_10].onClickDec()
            delay(5)

            assertEquals(
                expected = 99,
                actual = amountData.num,
            )
        }
    }

    /**
     * 最小個数への減算
     */
    @Test
    fun decTo0() {
        runBlocking {
            amountData.set(5)

            amountData.buttonDataList[DIGIT_10].onClickDec()
            delay(5)

            assertEquals(
                expected = 1,
                actual = amountData.num,
            )
        }
    }

    companion object {
        const val DIGIT_1 = 1
        const val DIGIT_10 = 0
    }
}
