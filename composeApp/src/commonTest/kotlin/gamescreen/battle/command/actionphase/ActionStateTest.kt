package gamescreen.battle.command.actionphase

import core.domain.status.ConditionType
import core.domain.status.damage100
import core.domain.status.damage50
import core.domain.status.paralysis100
import core.domain.status.paralysis50
import core.domain.status.poison0
import core.domain.status.poison100
import core.domain.status.poison50
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ActionStateTest {
    private lateinit var initState: ActionState
    private lateinit var conditionList: List<ConditionType>

    val next
        get() = initState.getNextState(conditionList)

    private val repeatTime = 100
    private val lower = (repeatTime * 0.8).toInt()
    private val upper = (repeatTime * 1.2).toInt()


    /**
     * 何もないならアクションに入ることを確認
     */
    @Test
    fun checkStartToAction() {
        initState = ActionState.Start
        conditionList = listOf()

        assertEquals(
            expected = ActionState.Action,
            actual = next
        )
    }

    /**
     * 必ず麻痺なら麻痺に入ることを確認
     */
    @Test
    fun checkStartToParalyze() {
        initState = ActionState.Start
        conditionList = listOf(paralysis100)

        assertEquals(
            expected = ActionState.Paralyze,
            actual = next
        )
    }

    /**
     * 確率で麻痺なら麻痺とアクションがあることを確認
     */
    @Test
    fun checkStartToProbParalyze() {
        initState = ActionState.Start
        conditionList = listOf(paralysis50)
        List(repeatTime * 2) {
            next
        }.apply {
            checkInRange(this.count {
                it == ActionState.Action
            })

            checkInRange(this.count {
                it == ActionState.Paralyze
            })
        }
    }

    /**
     * 麻痺で状態異常がない場合のテスト
     */
    // fixme 麻痺の場合の処理をViewModelから抽出する
//    @Test
//    fun checkParalyze(){
//        initState = ActionState.Paralyze
//        conditionList = listOf()
//
//        assertEquals(
//            expected = ActionState.Next,
//            actual = next,
//        )
//    }

    /**
     * 状態異常がない場合の行動後の遷移を確認
     */
    @Test
    fun checkAction() {
        initState = ActionState.Action
        conditionList = listOf()

        assertEquals(
            expected = ActionState.Next,
            actual = next,
        )
    }

    /**
     * 毒の場合の行動後の遷移を確認
     */
    @Test
    fun checkActionToPoison() {
        initState = ActionState.Action
        conditionList = listOf(poison100)

        assertEquals(
            expected = ActionState.Poison(damage = damage100),
            actual = next,
        )
    }

    @Test
    fun checkPoisonDuplicated() {
        initState = ActionState.Action
        conditionList = listOf(poison100, poison50)

        assertEquals(
            expected = ActionState.Poison(damage = damage100 + damage50),
            actual = next,
        )
    }

    /**
     * ダメージがない場合の毒の遷移
     */
    @Test
    fun checkActionToPoison0() {
        initState = ActionState.Action
        conditionList = listOf(poison0)

        List(repeatTime * 2) {
            next
        }.apply {
            checkInRange(this.count {
                it == ActionState.CurePoison()
            })

            checkInRange(this.count {
                it == ActionState.Next
            })
        }
    }


    /**
     * 必ず治る毒
     */
    @Test
    fun checkPoison() {
        initState = ActionState.Poison()
        conditionList = listOf(poison100)

        List(repeatTime) {
            next
        }.map {
            assertTrue {
                it == ActionState.CurePoison()
            }
        }
    }

    /**
     * 確率で治る毒
     */
    @Test
    fun checkPoisonProb() {
        initState = ActionState.Poison()
        conditionList = listOf(poison50)

        List(repeatTime * 2) {
            next
        }.apply {
            checkInRange(this.count {
                it == ActionState.CurePoison()
            })

            checkInRange(this.count {
                it == ActionState.Next
            })
        }
    }

    /**
     * 毒が2つある場合のテスト
     */
    @Test
    fun checkTwoPoison() {
        initState = ActionState.Poison()
        conditionList = listOf(poison100, poison50)

        List(repeatTime * 2) {
            next
        }.apply {
            checkInRange(
                this.count {
                    it == ActionState.CurePoison(listOf(poison50))
                }
            )

            checkInRange(
                this.count {
                    it == ActionState.CurePoison()
                }
            )
        }
    }

    /**
     * 必ず治る麻痺
     */
    @Test
    fun checkParalyze() {
        initState = ActionState.Action
        conditionList = listOf(paralysis100)

        List(repeatTime) {
            next
        }.map {
            assertEquals(
                expected = ActionState.CureParalyze(),
                actual = it,
            )
        }
    }

    /**
     * 確率で治る麻痺
     */
    @Test
    fun checkParalyzeProb() {
        initState = ActionState.Action
        conditionList = listOf(paralysis50)
        val expectedList: List<(ActionState) -> Boolean> = listOf(
            { it == ActionState.CureParalyze() },
            { it == ActionState.Next }
        )

        List(repeatTime * expectedList.size) {
            next
        }.apply {
            expectedList.map { counter ->
                checkInRange(
                    this.count {
                        counter(it)
                    }
                )
            }
        }
    }

    private fun checkInRange(count: Int) {
        assertTrue {
            lower <= count
        }

        assertTrue {
            count <= upper
        }
    }
}
