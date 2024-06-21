package domain.common.status

import common.status.param.HP
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class HpTest {

    private lateinit var hp: HP

    private val maxHP = 10
    private val hpValue = 10

    @BeforeTest
    fun beforeTest() {
        hp = HP(
            maxValue = maxHP,
            value = hpValue
        )
    }

    @Test
    fun setValueTest_10() {
        val value = 10
        hp.point = value
        assertEquals(
            expected = value,
            actual = hp.point
        )
    }

    @Test
    fun setValueTest_11() {
        val value = 11
        hp.point = value
        assertEquals(
            expected = maxHP,
            actual = hp.point
        )
    }

    @Test
    fun decValue() {
        val dec = 9
        hp.point -= dec
        assertEquals(
            expected = hpValue - dec,
            actual = hp.point
        )

        // hpは0以下にならない
        hp.point -= dec
        assertEquals(
            expected = 0,
            actual = hp.point
        )
    }

    @Test
    fun setMaxValueTest_15() {
        val value = 15
        hp.maxPoint = value
        assertEquals(
            expected = value,
            actual = hp.maxPoint
        )
        assertEquals(
            expected = hpValue,
            actual = hp.point,
        )
    }

    @Test
    fun setMaxValueTest_9() {
        val value = 9
        hp.maxPoint = value
        assertEquals(
            expected = value,
            actual = hp.maxPoint
        )
        assertEquals(
            expected = value,
            actual = hp.point,
        )
    }

    @Test
    fun decMaxValueTest_9() {
        val value = 9
        hp.maxPoint -= value
        assertEquals(
            expected = maxHP - value,
            actual = hp.maxPoint
        )
        assertEquals(
            expected = maxHP - value,
            actual = hp.point,
        )

        hp.maxPoint -= value
        assertEquals(
            expected = HP.MIN_MAX_VALUE,
            actual = hp.maxPoint
        )
        assertEquals(
            expected = HP.MIN_MAX_VALUE,
            actual = hp.point,
        )
    }
}
