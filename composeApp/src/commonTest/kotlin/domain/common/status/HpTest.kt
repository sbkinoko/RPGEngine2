package domain.common.status

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
}
