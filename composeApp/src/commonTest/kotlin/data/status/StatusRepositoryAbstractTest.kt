package data.status

import core.domain.status.PlayerStatus
import core.domain.status.StatusIncrease
import core.domain.status.StatusIncreaseTest
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV1_HP
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV1_MP
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV1_SPEED
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV2_HP
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV2_MP
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV2_SPEED
import core.domain.status.param.EXP
import kotlin.test.Test
import kotlin.test.assertEquals

class StatusRepositoryAbstractTest {
    private val skillListValue = listOf(1)
    private val toolListValue = listOf(2)

    private val nameValue = "test"
    private val expValue = EXP(
        listOf(
            2,
            2,
        )
    )

    private val testRepository: StatusRepository = object : StatusRepositoryAbstract() {
        override val statusUpList: List<List<StatusIncrease>>
            get() = listOf(
                StatusIncreaseTest.testStatusUpList
            )

        override val statusBaseList: List<PlayerStatus>
            get() = listOf(
                PlayerStatus(
                    toolList = toolListValue,
                    skillList = skillListValue,
                    name = nameValue,
                    exp = expValue,
                )
            )
    }

    @Test
    fun lv1Param() {
        val status = testRepository.getStatus(
            id = 0,
            level = 1,
        )

        status.apply {
            assertEquals(
                expected = TEST_LV1_HP,
                actual = hp.maxValue,
            )

            assertEquals(
                expected = TEST_LV1_MP,
                actual = mp.maxValue,
            )

            assertEquals(
                expected = TEST_LV1_SPEED,
                actual = speed,
            )

            assertEquals(
                expected = toolListValue,
                actual = toolList,
            )

            assertEquals(
                expected = skillListValue,
                actual = skillList,
            )

            assertEquals(
                expected = expValue,
                actual = exp,
            )
        }
    }

    @Test
    fun lv2Param() {
        val status = testRepository.getStatus(
            id = 0,
            level = 2,
        )

        status.apply {
            assertEquals(
                expected = TEST_LV1_HP + TEST_LV2_HP,
                actual = hp.maxValue,
            )

            assertEquals(
                expected = TEST_LV1_MP + TEST_LV2_MP,
                actual = mp.maxValue,
            )

            assertEquals(
                expected = TEST_LV1_SPEED + TEST_LV2_SPEED,
                actual = speed
            )

            assertEquals(
                expected = toolListValue,
                actual = toolList,
            )

            assertEquals(
                expected = skillListValue,
                actual = skillList,
            )

            assertEquals(
                expected = expValue,
                actual = exp,
            )
        }
    }
}
