package gamescreen.battle.service.monster

import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import gamescreen.battle.domain.ActionData
import kotlin.test.Test
import kotlin.test.assertTrue

class RandomTest {
    private val decideMonsterActionService = DecideMonsterActionService()

    private val monster = TestActiveMonster

    private val skillList1 = listOf(1, 2, 6)
    private val skillList2 = listOf(3, 5)

    private val players1 = listOf(
        testActivePlayer,
        testActivePlayer,
        testActivePlayer,
    )

    private val players2 = listOf(
        testActivePlayer,
        testActivePlayer,
        testActivePlayer,
    )

    private val num = 100
    private val low = (num * 0.8).toInt()
    private val high = (num * 1.2).toInt()

    @Test
    fun skillId1() {
        val resultList = mutableListOf<ActionData>()

        val testMonster = monster.copy(
            skillList = skillList1
        )

        val kind = skillList1.size
        repeat(kind * num) {
            resultList += decideMonsterActionService.getAction(
                testMonster,
                players1
            )
        }

        skillList1.forEach {
            val count = resultList.count { action ->
                action.skillId == it
            }

            count in low..high
        }
    }

    @Test
    fun skillId2() {
        val resultList = mutableListOf<ActionData>()

        val testMonster = monster.copy(
            skillList = skillList2
        )

        repeat(2 * num) {
            resultList += decideMonsterActionService.getAction(
                testMonster,
                players1
            )
        }

        skillList2.forEach {
            assertTrue {
                val count = resultList.count { action ->
                    action.skillId == it
                }

                count in low..high
            }
        }
    }

    @Test
    fun player1() {
        val resultList = mutableListOf<ActionData>()

        val testMonster = monster.copy(
            skillList = skillList1
        )

        repeat(players1.size * num) {
            resultList += decideMonsterActionService.getAction(
                testMonster,
                players1
            )
        }

        players1.forEachIndexed { index, _ ->
            assertTrue {
                val count = resultList.count { action ->
                    action.target == index
                }

                count in low..high
            }
        }
    }

    @Test
    fun player2() {
        val resultList = mutableListOf<ActionData>()

        val testMonster = monster.copy(
            skillList = skillList1
        )

        repeat(players2.size * num) {
            resultList += decideMonsterActionService.getAction(
                testMonster,
                players2
            )
        }

        players2.forEachIndexed { index, _ ->
            assertTrue {
                val count = resultList.count { action ->
                    action.target == index
                }

                count in low..high
            }
        }
    }
}
