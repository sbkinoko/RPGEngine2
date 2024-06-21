package battle.manger

import battle.manager.AttackManager
import common.status.MonsterStatusTest.Companion.getMonster
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AttackManagerTest {

    private lateinit var attackManager: AttackManager

    @BeforeTest
    fun beforeTest() {
        attackManager = AttackManager()
    }

    @Test
    fun attackTo1() {
        val monsters = List(1) {
            getMonster()
        }

        val id = 0
        val damage = 5

        val newMonsters = attackManager.attack(
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

        attackManager.attack(
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
