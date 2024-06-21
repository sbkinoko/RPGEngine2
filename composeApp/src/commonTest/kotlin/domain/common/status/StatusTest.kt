package domain.common.status

import common.status.PlayerStatus
import common.status.Status
import common.status.param.HP
import common.status.param.MP
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StatusTest {
    private lateinit var status: Status

    @BeforeTest
    fun beforeTest() {
        status = PlayerStatus(
            name = "test",
            hp = HP(
                maxValue = 100,
            ),
            mp = MP(
                maxValue = 100,
            )
        )
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
