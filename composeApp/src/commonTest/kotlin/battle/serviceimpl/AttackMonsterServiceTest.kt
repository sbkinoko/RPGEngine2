package battle.serviceimpl

import common.status.MonsterStatusTest.Companion.getMonster
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AttackMonsterServiceTest {

    private lateinit var attackMonsterService: AttackMonsterService

    @BeforeTest
    fun beforeTest() {
        attackMonsterService = AttackMonsterService()
    }

    @Test
    fun attackTo1() {
        val monsters = List(1) {
            getMonster()
        }

        val id = 0
        val damage = 5

        val newMonsters = attackMonsterService.attack(
            target = id,
            damage = damage,
            monsters = monsters
        )

        newMonsters[id].hp.apply {
            assertEquals(
                expected = maxPoint - damage,
                actual = point
            )
        }
    }

    @Test
    fun attackTo2When2Monster() {
        val monsters = List(2) {
            getMonster()
        }

        val id = 1
        val damage = 5

        attackMonsterService.attack(
            target = id,
            damage = damage,
            monsters = monsters,
        ).let {
            it[0].hp.apply {
                assertEquals(
                    expected = maxPoint,
                    actual = point
                )
            }

            it[1].hp.apply {
                assertEquals(
                    expected = maxPoint - damage,
                    actual = point
                )
            }
        }
    }
}
