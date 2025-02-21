package core.domain.status

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ConditionTypeTest {
    private lateinit var conditionList: List<ConditionType>

    private val repeatTime = 100

    @Test
    fun checkCurePoison() {
        conditionList = listOf(
            poison100,
        )

        val after = List(repeatTime) {
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
        conditionList = listOf(
            ConditionType.Poison(cure = 70),
        )

        val after = List(repeatTime * 10) {
            conditionList.tryCure<ConditionType.Poison>()
        }

        val cureNum = after.count {
            it.isEmpty()
        }
        assertTrue {
            cureNum in (repeatTime * 7 * 0.8).toInt()..(repeatTime * 7 * 1.2).toInt()
        }
    }

    @Test
    fun checkCurePoisonProb2() {
        conditionList = listOf(
            poisonCure70,
            poisonCure40,
        )

        val after = List(repeatTime * 10) {
            conditionList.tryCure<ConditionType.Poison>()
        }

        val totalLower = (repeatTime * 10 * 0.8).toInt()
        val totalUpper = (repeatTime * 10 * 1.2).toInt()

        after.apply {
            assertTrue {
                // 0.3 * 0.4
                val prob = 0.12
                count {
                    it == listOf(
                        poisonCure70,
                    )
                } in (totalLower * prob).toInt()..(totalUpper * prob).toInt()
            }
            assertTrue {
                // 0.7 * 0.6
                val prob = 0.42
                count {
                    it == listOf(
                        poisonCure40,
                    )
                } in (totalLower * prob).toInt()..(totalUpper * prob).toInt()
            }

            assertTrue {
                // 0.7 * 0.4
                val prob = 0.28
                count {
                    it == emptyList<ConditionType>()
                } in (totalLower * prob).toInt()..(totalUpper * prob).toInt()
            }

            assertTrue {
                // 0.3 * 0.6
                val prob = 0.18
                count {
                    it == conditionList
                } in (totalLower * prob).toInt()..(totalUpper * prob).toInt()
            }
        }
    }

    @Test
    fun checkCurePoisonProbAndParalyze() {
        conditionList = listOf(
            ConditionType.Poison(cure = 70),
            ConditionType.Paralysis(),
        )

        val after = List(repeatTime * 10) {
            conditionList.tryCure<ConditionType.Poison>()
        }

        val cureNum = after.count {
            it == listOf(ConditionType.Paralysis())
        }

        assertTrue {
            cureNum in (repeatTime * 7 * 0.8).toInt()..(repeatTime * 7 * 1.2).toInt()
        }
    }
}
