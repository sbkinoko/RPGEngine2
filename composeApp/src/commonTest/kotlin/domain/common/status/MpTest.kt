package domain.common.status

import domain.common.status.param.MP
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MpTest {

    private lateinit var mp: MP

    private val maxMP = 10
    private val mpValue = 10

    @BeforeTest
    fun beforeTest() {
        mp = MP(
            maxValue = maxMP,
            value = mpValue
        )
    }

    @Test
    fun setValueTest_10() {
        val value = 10
        mp.point = value
        assertEquals(
            expected = value,
            actual = mp.point
        )
    }

    @Test
    fun setValueTest_11() {
        val value = 11
        mp.point = value
        assertEquals(
            expected = maxMP,
            actual = mp.point
        )
    }

    @Test
    fun decValue() {
        val dec = 9
        mp.point -= dec
        assertEquals(
            expected = mpValue - dec,
            actual = mp.point
        )

        // mpは0以下にならない
        mp.point -= dec
        assertEquals(
            expected = 0,
            actual = mp.point
        )
    }

    @Test
    fun setMaxValueTest_15() {
        val value = 15
        mp.maxPoint = value
        assertEquals(
            expected = value,
            actual = mp.maxPoint
        )
        assertEquals(
            expected = mpValue,
            actual = mp.point,
        )
    }

    @Test
    fun setMaxValueTest_9() {
        val value = 9
        mp.maxPoint = value
        assertEquals(
            expected = value,
            actual = mp.maxPoint
        )
        assertEquals(
            expected = value,
            actual = mp.point,
        )
    }

    @Test
    fun decMaxValueTest_9() {
        val value = 9
        mp.maxPoint -= value
        assertEquals(
            expected = maxMP - value,
            actual = mp.maxPoint
        )
        assertEquals(
            expected = maxMP - value,
            actual = mp.point,
        )

        mp.maxPoint -= value
        assertEquals(
            expected = MP.MIN_MAX_VALUE,
            actual = mp.maxPoint
        )
        assertEquals(
            expected = MP.MIN_MAX_VALUE,
            actual = mp.point,
        )
    }
}
