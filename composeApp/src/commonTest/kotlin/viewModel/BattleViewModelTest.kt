package viewModel

import domain.common.status.MonsterStatus
import viewmodel.BattleViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BattleViewModelTest {
    private lateinit var battleViewModel: BattleViewModel

    private val maxHP = 10

    @BeforeTest
    fun beforeTest() {
        battleViewModel = BattleViewModel()
    }

    @Test
    fun attackTo1() {
        battleViewModel.monsters = List(1) {
            MonsterStatus(
                imgId = 1,
                name = "テスト",
            ).apply {
                hp.apply {
                    maxPoint = maxHP
                    point = maxHP
                }
            }
        }

        val id = 0
        val damage = 5
        battleViewModel.attack(
            target = id,
            damage = damage
        )

        battleViewModel.monsters[id].hp.apply {
            assertEquals(
                expected = maxPoint - damage,
                actual = point
            )
        }
    }

    @Test
    fun attackTo2When2Monster() {
        battleViewModel.monsters = List(2) {
            MonsterStatus(
                imgId = 1,
                name = "テスト",
            ).apply {
                hp.apply {
                    maxPoint = maxHP
                    point = maxHP
                }
            }
        }

        val id = 1
        val damage = 5
        battleViewModel.attack(
            target = id,
            damage = damage
        )

        battleViewModel.monsters[0].hp.apply {
            assertEquals(
                expected = maxPoint,
                actual = point
            )
        }

        battleViewModel.monsters[1].hp.apply {
            assertEquals(
                expected = maxPoint - damage,
                actual = point
            )
        }
    }

    @Test
    fun attackTo1When1IsNotActive() {
        battleViewModel.monsters = List(2) {
            MonsterStatus(
                imgId = 1,
                name = "テスト",
            ).apply {
                hp.apply {
                    maxPoint = maxHP
                    point = maxHP
                }
            }
        }

        val id = 0
        val damage = 10
        battleViewModel.attack(
            target = id,
            damage = damage
        )

        battleViewModel.monsters[0].hp.apply {
            assertEquals(
                expected = 0,
                actual = point
            )
        }

        battleViewModel.monsters[1].hp.apply {
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

        battleViewModel.monsters[0].hp.apply {
            assertEquals(
                expected = 0,
                actual = point
            )
        }

        battleViewModel.monsters[1].hp.apply {
            assertEquals(
                expected = 0,
                actual = point
            )
        }
    }

    @Test
    fun attackTo2When2IsNotActive() {
        battleViewModel.monsters = List(2) {
            MonsterStatus(
                imgId = 1,
                name = "テスト",
            ).apply {
                hp.apply {
                    maxPoint = maxHP
                    point = maxHP
                }
            }
        }

        val id = 1
        val damage = 10
        battleViewModel.attack(
            target = id,
            damage = damage
        )

        battleViewModel.monsters[0].hp.apply {
            assertEquals(
                expected = maxPoint,
                actual = point
            )
        }

        battleViewModel.monsters[1].hp.apply {
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

        battleViewModel.monsters[0].hp.apply {
            assertEquals(
                expected = 0,
                actual = point
            )
        }

        battleViewModel.monsters[1].hp.apply {
            assertEquals(
                expected = 0,
                actual = point
            )
        }
    }

    @Test
    fun attackTo1When1And2IsNotActive() {
        battleViewModel.monsters = List(3) {
            MonsterStatus(
                imgId = 1,
                name = "テスト",
            ).apply {
                hp.apply {
                    maxPoint = maxHP
                    point = maxHP
                }
            }
        }

        val id = 0
        val damage = 10

        battleViewModel.attack(
            target = id,
            damage = damage
        )
        battleViewModel.monsters[0].hp.apply {
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
        battleViewModel.monsters[1].hp.apply {
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
        battleViewModel.monsters[2].hp.apply {
            assertEquals(
                expected = 0,
                actual = point
            )
        }
    }

    @Test
    fun checkIsBattleFinish() {
        battleViewModel.monsters = List(3) {
            MonsterStatus(
                imgId = 1,
                name = "テスト",
            ).apply {
                hp.apply {
                    maxPoint = maxHP
                    point = maxHP
                }
            }
        }

        val id = 0
        val damage = 10

        battleViewModel.attack(
            target = id,
            damage = damage
        )
        assertEquals(
            expected = false,
            actual = battleViewModel.isBattleFinish
        )

        // 隣を攻撃
        battleViewModel.attack(
            target = id,
            damage = damage
        )
        assertEquals(
            expected = false,
            actual = battleViewModel.isBattleFinish
        )

        // 隣の隣を攻撃
        battleViewModel.attack(
            target = id,
            damage = damage
        )
        assertEquals(
            expected = true,
            actual = battleViewModel.isBattleFinish
        )
    }
}
