package gamescreen.battle.command.actionphase

import constants.REPEAT_TIME
import constants.isInRange
import core.domain.status.ConditionType
import core.domain.status.damage100
import core.domain.status.damage50
import core.domain.status.paralysis100
import core.domain.status.paralysis30
import core.domain.status.poison0
import core.domain.status.poison100
import core.domain.status.poison50
import core.domain.status.poisonCure70
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ActionStateTest {
    private lateinit var initState: ActionState
    private lateinit var conditionList: List<ConditionType>

    val next
        get() = initState.getNextState(conditionList)

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
        val paralyze = paralysis30
        conditionList = listOf(paralyze)
        List(REPEAT_TIME) {
            next
        }.apply {
            assertTrue {
                isInRange(
                    count {
                        it == ActionState.Action
                    },
                    100f - paralyze.probability
                )
            }

            assertTrue {
                isInRange(
                    count {
                        it == ActionState.Paralyze
                    },
                    paralyze.probability.toFloat()
                )
            }
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

        val poison = poison0
        conditionList = listOf(poison)

        List(REPEAT_TIME) {
            next
        }.apply {
            assertTrue {
                isInRange(
                    count {
                        it == ActionState.CurePoison()
                    },
                    poison.cure.toFloat()
                )
            }

            assertTrue {
                isInRange(
                    count {
                        it == ActionState.Next
                    },
                    100 - poison.cure.toFloat()
                )
            }
        }
    }


    /**
     * 必ず治る毒
     */
    @Test
    fun checkPoison() {
        initState = ActionState.Poison()
        conditionList = listOf(poison100)

        List(REPEAT_TIME) {
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
        val poison = poisonCure70
        conditionList = listOf(poison)

        List(REPEAT_TIME) {
            next
        }.apply {
            assertTrue {
                isInRange(
                    count {
                        it == ActionState.CurePoison()
                    },
                    poison.cure.toFloat()
                )
            }

            assertTrue {
                isInRange(
                    count {
                        it == ActionState.Next
                    },
                    100 - poison.cure.toFloat()
                )
            }
        }
    }

    /**
     * 毒が2つある場合のテスト
     */
    @Test
    fun checkTwoPoison() {
        initState = ActionState.Poison()
        val poison = poisonCure70
        conditionList = listOf(poison100, poison)

        List(REPEAT_TIME) {
            next
        }.apply {
            // 片方治らない
            assertTrue {
                isInRange(
                    count {
                        it == ActionState.CurePoison(listOf(poison))
                    },
                    100 - poison.cure.toFloat(),
                )
            }

            // どっちも治る
            assertTrue {
                isInRange(
                    count {
                        it == ActionState.CurePoison()
                    },
                    poison.cure.toFloat(),
                )
            }
        }
    }

    /**
     * 必ず治る麻痺
     */
    @Test
    fun checkParalyze() {
        initState = ActionState.Action
        conditionList = listOf(paralysis100)

        List(REPEAT_TIME) {
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
        val paralysis = paralysis30
        conditionList = listOf(paralysis)

        // 確率　状態
        val expectedList: List<Pair<Float, ActionState>> = listOf(
            Pair(paralysis.cure.toFloat(), ActionState.CureParalyze()),
            Pair(100f - paralysis.cure.toFloat(), ActionState.Next),
        )

        List(REPEAT_TIME) {
            next
        }.apply {
            expectedList.map { p ->
                assertTrue {
                    isInRange(
                        count {
                            it == p.second
                        },
                        p.first,
                    )
                }
            }
        }
    }
}
