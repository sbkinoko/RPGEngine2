package battle.viewmodel

import common.status.MonsterStatusTest.Companion.getMonster
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BattleViewModelTest {
    private lateinit var battleViewModel: BattleViewModel

    @BeforeTest
    fun beforeTest() {
        battleViewModel = BattleViewModel()
        battleViewModel.pressB = {}
    }


    @Test
    fun checkIsBattleFinish() {
        battleViewModel.setMonsters(
            MutableList(3) {
                getMonster()
            }
        )

        val id = 0
        val damage = 10

        battleViewModel.attack(
            target = id,
            damage = damage
        )
        assertEquals(
            expected = false,
            actual = battleViewModel.isAllMonsterNotActive
        )

        // 隣を攻撃
        battleViewModel.attack(
            target = id,
            damage = damage
        )
        assertEquals(
            expected = false,
            actual = battleViewModel.isAllMonsterNotActive
        )

        // 隣の隣を攻撃
        battleViewModel.attack(
            target = id,
            damage = damage
        )
        assertEquals(
            expected = true,
            actual = battleViewModel.isAllMonsterNotActive
        )
    }
}