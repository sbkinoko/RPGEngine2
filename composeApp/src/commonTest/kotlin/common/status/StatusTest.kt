package common.status

import core.domain.status.PlayerStatus
import core.domain.status.Status
import core.domain.status.param.EXP
import core.domain.status.param.HP
import core.domain.status.param.MP
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
            ),
            skillList = listOf(),
            toolList = listOf(),
            exp = EXP(
                EXP.type1,
            ),
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
