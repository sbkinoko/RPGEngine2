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
        val monster = getMonster()

        val id = 0
        val damage = 5

        val newMonsters = attackMonsterService.attack(
            target = id,
            damage = damage,
            monster = monster,
        )

        newMonsters.hp.apply {
            assertEquals(
                expected = maxPoint - damage,
                actual = point
            )
        }
    }
}
