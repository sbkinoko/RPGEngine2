package core.domain.status

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ParalyzeTest {
    private val repeatTime = 100
    private val lower = (repeatTime * 0.8).toInt()
    private val upper = (repeatTime * 1.2).toInt()

    @Test
    fun cantMove() {
        List(repeatTime) {
            listOf(ConditionType.Paralysis(probability = 100)).canMove()
        }.map {
            assertFalse {
                it
            }
        }
    }

    @Test
    fun canMove() {
        List(repeatTime) {
            listOf(ConditionType.Paralysis(probability = 0)).canMove()
        }.map {
            assertTrue {
                it
            }
        }
    }


    @Test
    fun half() {
        List(repeatTime * 2) {
            listOf(ConditionType.Paralysis(probability = 50)).canMove()
        }.apply {
            assertTrue {
                count {
                    it
                } in lower..upper
            }

            assertTrue {
                count {
                    !it
                } in lower..upper
            }
        }
    }
}
