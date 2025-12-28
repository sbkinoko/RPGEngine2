package data.status

import core.domain.status.PlayerStatus
import core.domain.status.StatusData
import core.domain.status.StatusIncrease
import core.domain.status.StatusIncreaseTest
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV1_ATK
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV1_DEF
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV1_HP
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV1_MP
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV1_SPEED
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV2_ATK
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV2_DEF
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV2_HP
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV2_MP
import core.domain.status.StatusIncreaseTest.Companion.TEST_LV2_SPEED
import core.domain.status.job.Job
import core.domain.status.param.EXP
import core.domain.status.param.StatusParameter
import data.repository.item.skill.SkillId
import data.repository.item.tool.ToolId
import data.repository.status.AbstractStatusRepository
import data.repository.status.StatusRepository
import kotlin.test.Test
import kotlin.test.assertEquals

class AbstractCharacterRepositoryTest {
    private val skillListValue = listOf(SkillId.Normal1)
    private val toolListValue = listOf(ToolId.HEAL1)

    private val nameValue = "test"
    private val expValue = EXP(
        listOf(
            2,
            2,
        )
    )

    private val testRepository: StatusRepository = object : AbstractStatusRepository() {
        override val statusUpList: List<List<StatusIncrease>>
            get() = listOf(
                StatusIncreaseTest.testStatusUpList
            )

        override val statusBaseList: List<Pair<PlayerStatus, StatusData>>
            get() = listOf(
                Pair(
                    PlayerStatus(
                        toolList = toolListValue,
                        skillList = skillListValue,
                        exp = expValue,
                        job = Job.Warrior,
                    ),
                    StatusData(
                        name = nameValue
                    ),
                )
            )
    }

    @Test
    fun lv1Param() {
        val status = testRepository.getStatus(
            id = 0,
            exp = 0,
        )

        status.first.apply {
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

        status.second.apply {
            assertEquals(
                expected = TEST_LV1_HP,
                actual = hp.maxPoint,
            )

            assertEquals(
                expected = TEST_LV1_MP,
                actual = mp.maxPoint,
            )

            assertEquals(
                expected = StatusParameter(TEST_LV1_ATK),
                actual = atk
            )

            assertEquals(
                expected = StatusParameter(TEST_LV1_DEF),
                actual = def
            )

            assertEquals(
                expected = StatusParameter(TEST_LV1_SPEED),
                actual = speed,
            )
        }
    }

    @Test
    fun lv2Param() {
        val expNum = 2
        val status = testRepository.getStatus(
            id = 0,
            exp = expNum,
        )

        status.first.apply {
            assertEquals(
                expected = toolListValue,
                actual = toolList,
            )

            assertEquals(
                expected = skillListValue,
                actual = skillList,
            )

            assertEquals(
                expected = expValue.copy(
                    value = expNum,
                ),
                actual = exp,
            )
        }

        status.second.apply {
            assertEquals(
                expected = TEST_LV1_HP + TEST_LV2_HP,
                actual = hp.maxPoint,
            )

            assertEquals(
                expected = TEST_LV1_MP + TEST_LV2_MP,
                actual = mp.maxPoint,
            )

            assertEquals(
                expected = StatusParameter(TEST_LV1_ATK + TEST_LV2_ATK),
                actual = atk,
            )

            assertEquals(
                expected = StatusParameter(TEST_LV1_DEF + TEST_LV2_DEF),
                actual = def,
            )

            assertEquals(
                expected = StatusParameter(TEST_LV1_SPEED + TEST_LV2_SPEED),
                actual = speed,
            )
        }
    }
}
