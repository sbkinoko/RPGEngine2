package core.domain.status

import constants.REPEAT_TIME
import constants.isInRange
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ParalyzeTest {

    @Test
    fun cantMove() {
        List(REPEAT_TIME) {
            listOf(ConditionType.Paralysis(probability = 100)).canMove()
        }.map {
            assertFalse {
                it
            }
        }
    }

    @Test
    fun canMove() {
        List(REPEAT_TIME) {
            listOf(ConditionType.Paralysis(probability = 0)).canMove()
        }.map {
            assertTrue {
                it
            }
        }
    }

    @Test
    fun half() {
        val paralyze = paralysis50

        List(REPEAT_TIME) {
            listOf(paralyze).canMove()
        }.apply {

            // 動ける確率
            assertTrue {
                isInRange(
                    count {
                        it
                    },
                    100 - paralyze.probability.toFloat()
                )
            }

            //　動けない確率
            assertTrue {
                isInRange(
                    count {
                        !it
                    },
                    paralyze.probability.toFloat()
                )
            }
        }
    }

    @Test
    fun prob30() {
        val paralyze = paralysis30

        List(REPEAT_TIME) {
            listOf(paralyze).canMove()
        }.apply {

            // 動ける確率
            assertTrue {
                isInRange(
                    count {
                        it
                    },
                    100 - paralyze.probability.toFloat()
                )
            }

            //　動けない確率
            assertTrue {
                isInRange(
                    count {
                        !it
                    },
                    paralyze.probability.toFloat()
                )
            }
        }
    }
}
