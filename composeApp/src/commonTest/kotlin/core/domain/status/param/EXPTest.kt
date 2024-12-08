package core.domain.status.param

import values.TextData.Companion.NEED_EXP_MAX_LEVEL
import kotlin.test.Test
import kotlin.test.assertEquals

class EXPTest {
    private val MAX_LEVEL = 5
    private val expList = List<Int>(MAX_LEVEL - 1) {
        1
    }

    @Test
    fun initial() {
        val exp = EXP(expList)

        assertEquals(
            expected = EXP.INITIAL_LEVEL,
            actual = exp.level
        )
        assertEquals(
            expected = EXP.INITIAL_EXP,
            actual = exp.exp
        )
    }

    @Test
    fun middleExp() {
        val exp = EXP(expList)
        exp.exp += 1

        assertEquals(
            expected = 2,
            actual = exp.level
        )
    }

    @Test
    fun maxExp() {
        val exp = EXP(expList)
        exp.exp = MAX_LEVEL - 1
        assertEquals(
            expected = MAX_LEVEL,
            actual = exp.level

        )

        exp.exp += 100

        assertEquals(
            expected = MAX_LEVEL,
            actual = exp.level
        )
    }

    @Test
    fun needExp() {
        val exp = EXP(
            listOf(
                10,
                10,
            )
        )
        //0なので10必要
        assertEquals(
            expected = "10",
            actual = exp.needExp
        )

        exp.exp = 9
        assertEquals(
            expected = "1",
            actual = exp.needExp
        )

        exp.exp = 10
        assertEquals(
            expected = "10",
            actual = exp.needExp
        )

        exp.exp = 19
        assertEquals(
            expected = "1",
            actual = exp.needExp
        )

        exp.exp = 20
        assertEquals(
            expected = NEED_EXP_MAX_LEVEL,
            actual = exp.needExp
        )

        exp.exp = 21
        assertEquals(
            expected = NEED_EXP_MAX_LEVEL,
            actual = exp.needExp
        )
    }
}
