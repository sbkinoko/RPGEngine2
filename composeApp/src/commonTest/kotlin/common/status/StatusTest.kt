package common.status

import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import core.domain.status.Status
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StatusTest {
    private lateinit var status: Status

    @BeforeTest
    fun beforeTest() {
        status = testActivePlayer
    }

    @Test
    fun isActive() {
        status.hp.point = 1
        assertEquals(
            expected = true,
            actual = status.isActive
        )
    }

    @Test
    fun isNotActive() {
        status.hp.point = 0
        assertEquals(
            expected = false,
            actual = status.isActive
        )
    }
}
