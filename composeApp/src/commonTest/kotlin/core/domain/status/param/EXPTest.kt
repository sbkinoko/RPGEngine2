package core.domain.status.param

import values.TextData.Companion.NEED_EXP_MAX_LEVEL
import kotlin.test.Test
import kotlin.test.assertEquals

class EXPTest {
    private val MAX_LEVEL = 5
    private val MAX_LEVEL_EXP = MAX_LEVEL - 1

    // 最初のレベルが1だから MAX LEVEL-1 の長さになる
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
            actual = exp.value
        )
    }

    @Test
    fun middleExp() {
        val exp = EXP(expList)
        val exp2 = exp.copy(
            value = exp.value + 1
        )

        assertEquals(
            expected = 2,
            actual = exp2.level
        )
    }

    @Test
    fun maxExp() {
        val exp = EXP(expList)

        val exp1 = exp.copy(
            value = MAX_LEVEL_EXP
        )

        assertEquals(
            expected = MAX_LEVEL,
            actual = exp1.level
        )

        val exp2 = exp.copy(
            value = exp1.value + 100
        )

        assertEquals(
            expected = MAX_LEVEL,
            actual = exp2.level
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

        assertEquals(
            expected = "1",
            actual = exp.copy(
                value = 9,
            ).needExp
        )

        assertEquals(
            expected = "10",
            actual = exp.copy(
                value = 10,
            ).needExp
        )

        assertEquals(
            expected = "1",
            actual = exp.copy(
                value = 19,
            ).needExp
        )

        assertEquals(
            expected = NEED_EXP_MAX_LEVEL,
            actual = exp.copy(
                value = 20,
            ).needExp
        )

        assertEquals(
            expected = NEED_EXP_MAX_LEVEL,
            actual = exp.copy(
                value = 21,
            ).needExp
        )
    }
}
