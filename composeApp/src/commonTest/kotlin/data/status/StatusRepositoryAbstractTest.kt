package data.status

import core.domain.status.PlayerStatus
import core.domain.status.param.EXP
import core.domain.status.param.HP
import core.domain.status.param.MP
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class StatusRepositoryAbstractTest {
    private val lv1HP = 10
    private val lv1MP = 11

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
                listOf(
                    StatusIncrease(
                        hp = lv1HP,
                        mp = lv1MP,
                    )
                )
            )

        override val statusBaseList: List<PlayerStatus>
            get() = listOf(
                PlayerStatus(
                    hp = HP(
                        maxValue = 0,
                    ),
                    mp = MP(
                        maxValue = 0,
                    ),
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
                expected = lv1HP,
                actual = hp.maxValue,
            )

            assertEquals(
                expected = lv1MP,
                actual = mp.maxValue,
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
