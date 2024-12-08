package core.domain.status.param

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
}
