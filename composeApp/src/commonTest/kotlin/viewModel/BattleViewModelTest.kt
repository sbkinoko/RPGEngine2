package viewModel

import domain.common.status.MonsterStatus
import domain.common.status.param.HP
import domain.common.status.param.MP
import viewmodel.BattleViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BattleViewModelTest {
    private lateinit var battleViewModel: BattleViewModel

    private val maxHP = 10
    private val maxMP = 10

    @BeforeTest
    fun beforeTest() {
        battleViewModel = BattleViewModel()
        battleViewModel.pressB = {}
    }

    @Test
    fun attackTo1() {
        battleViewModel._monsters.value = MutableList(1) {
            getMonster()
        }

        val id = 0
        val damage = 5
        battleViewModel.attack(
            target = id,
            damage = damage
        )

        battleViewModel.monsters.value[id].hp.apply {
            assertEquals(
                expected = maxPoint - damage,
                actual = point
            )
        }
    }

    @Test
    fun attackTo2When2Monster() {
        battleViewModel._monsters.value = MutableList(2) {
            getMonster()
        }

        val id = 1
        val damage = 5
        battleViewModel.attack(
            target = id,
            damage = damage
        )

        battleViewModel.monsters.value[0].hp.apply {
            assertEquals(
                expected = maxPoint,
                actual = point
            )
        }

        battleViewModel.monsters.value[1].hp.apply {
            assertEquals(
                expected = maxPoint - damage,
                actual = point
            )
        }
    }

    @Test
    fun attackTo1When1IsNotActive() {
        battleViewModel._monsters.value = MutableList(2) {
            getMonster()
        }

        val id = 0
        val damage = 10
        battleViewModel.attack(
            target = id,
            damage = damage
        )

        battleViewModel.monsters.value[0].hp.apply {
            assertEquals(
                expected = 0,
                actual = point
            )
        }

        battleViewModel.monsters.value[1].hp.apply {
            assertEquals(
                expected = maxPoint,
                actual = point
            )
        }

        // 対象のモンスターが倒れているときは
        // 隣のモンスター攻撃することを確認
        battleViewModel.attack(
            target = id,
            damage = damage
        )

        battleViewModel.monsters.value[0].hp.apply {
            assertEquals(
                expected = 0,
                actual = point
            )
        }

        battleViewModel.monsters.value[1].hp.apply {
            assertEquals(
                expected = 0,
                actual = point
            )
        }
    }

    @Test
    fun attackTo2When2IsNotActive() {
        battleViewModel._monsters.value = MutableList(2) {
            getMonster()
        }

        val id = 1
        val damage = 10
        battleViewModel.attack(
            target = id,
            damage = damage
        )

        battleViewModel.monsters.value[0].hp.apply {
            assertEquals(
                expected = maxPoint,
                actual = point
            )
        }

        battleViewModel.monsters.value[1].hp.apply {
            assertEquals(
                expected = 0,
                actual = point
            )
        }

        // 対象のモンスターが倒れているときは
        // 隣のモンスター攻撃することを確認
        battleViewModel.attack(
            target = id,
            damage = damage
        )

        battleViewModel.monsters.value[0].hp.apply {
            assertEquals(
                expected = 0,
                actual = point
            )
        }

        battleViewModel.monsters.value[1].hp.apply {
            assertEquals(
                expected = 0,
                actual = point
            )
        }
    }

    @Test
    fun attackTo1When1And2IsNotActive() {
        battleViewModel._monsters.value = MutableList(3) {
            getMonster()
        }

        val id = 0
        val damage = 10

        battleViewModel.attack(
            target = id,
            damage = damage
        )
        battleViewModel.monsters.value[0].hp.apply {
            assertEquals(
                expected = 0,
                actual = point
            )
        }

        // 隣を攻撃
        battleViewModel.attack(
            target = id,
            damage = damage
        )
        battleViewModel.monsters.value[1].hp.apply {
            assertEquals(
                expected = 0,
                actual = point
            )
        }

        // 隣の隣を攻撃
        battleViewModel.attack(
            target = id,
            damage = damage
        )
        battleViewModel.monsters.value[2].hp.apply {
            assertEquals(
                expected = 0,
                actual = point
            )
        }
    }

    @Test
    fun checkIsBattleFinish() {
        battleViewModel._monsters.value = MutableList(3) {
            getMonster()
        }

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

    private fun getMonster() = MonsterStatus(
        imgId = 1,
        name = "テスト",
        hp = HP(
            maxValue = maxHP,
        ),
        mp = MP(
            maxValue = maxMP,
        )
    )
}
