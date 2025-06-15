package common.status

import core.domain.status.StatusDataTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StatusTest {

    @Test
    fun isActive() {
        val updated = StatusDataTest.TestPlayerStatusActive.run {
            copy(
                hp = hp.set(value = 1)
            )
        }
        assertEquals(
            expected = true,
            actual = updated.isActive
        )
    }

    @Test
    fun isNotActive() {
        val updated = StatusDataTest.TestPlayerStatusActive.run {
            copy(
                hp = hp.set(value = 0)
            )
        }
        assertEquals(
            expected = false,
            actual = updated.isActive
        )
    }
}
