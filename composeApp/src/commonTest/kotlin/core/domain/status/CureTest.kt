package core.domain.status

import constants.REPEAT_TIME
import constants.isInRange
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ConditionTypeTest {
    private lateinit var conditionList: List<ConditionType>

    @Test
    fun checkCurePoison() {
        conditionList = listOf(
            poison100,
        )

        val after = List(REPEAT_TIME) {
            conditionList.tryCure<ConditionType.Poison>()
        }

        after.map {
            assertEquals(
                expected = emptyList(),
                actual = it,
            )
        }
    }

    @Test
    fun checkCurePoisonProb() {
        val poison = poisonCure70
        conditionList = listOf(
            poison,
        )

        val after = List(REPEAT_TIME) {
            conditionList.tryCure<ConditionType.Poison>()
        }

        val cureNum = after.count {
            it.isEmpty()
        }

        assertTrue {
            isInRange(
                cureNum,
                poison.cure.toFloat(),
            )
        }
    }

    @Test
    fun checkCurePoisonProb2() {
        conditionList = listOf(
            poisonCure70,
            poisonCure40,
        )

        val after = List(REPEAT_TIME) {
            conditionList.tryCure<ConditionType.Poison>()
        }

        after.apply {
            assertTrue {
                // 70が治らず、40が治った
                // 0.3 * 0.4
                val prob = 12f
                isInRange(
                    count {
                        it == listOf(
                            poisonCure70,
                        )
                    },
                    prob,
                )
            }

            assertTrue {
                // 7が治って、40が治らず
                // 0.7 * 0.6
                val prob = 42f
                isInRange(
                    count {
                        it == listOf(
                            poisonCure40,
                        )
                    },
                    prob,
                )
            }

            assertTrue {
                // 70で治って、40も治った
                // 0.7 * 0.4
                val prob = 28f

                isInRange(
                    count {
                        it == emptyList<ConditionType>()
                    },
                    prob,
                )
            }

            assertTrue {
                // 70で治らず、40も治らない
                // 0.3 * 0.6
                val prob = 18f

                isInRange(
                    count {
                        it == conditionList
                    },
                    prob,
                )
            }
        }
    }

    @Test
    fun checkCurePoisonProbAndParalyze() {
        val poison = poisonCure70
        conditionList = listOf(
            poison,
            ConditionType.Paralysis(),
        )

        val after = List(REPEAT_TIME) {
            conditionList.tryCure<ConditionType.Poison>()
        }

        val cureNum = after.count {
            it == listOf(ConditionType.Paralysis())
        }

        assertTrue {
            isInRange(
                cureNum,
                poison.cure.toFloat(),
            )
        }
    }
}
