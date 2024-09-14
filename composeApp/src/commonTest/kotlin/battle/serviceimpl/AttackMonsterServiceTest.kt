package battle.serviceimpl

import battle.service.attack.UpdateMonsterStatusService
import common.status.MonsterStatusTest.Companion.getMonster
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AttackMonsterServiceTest {

    private lateinit var attackMonsterService: UpdateMonsterStatusService

    @BeforeTest
    fun beforeTest() {
        attackMonsterService = UpdateMonsterStatusService()
    }

    @Test
    fun attackTo1() {
        val monster = getMonster()

        val damage = 5

        val newMonsters = attackMonsterService.decHP(
            amount = damage,
            status = monster,
        )

        newMonsters.hp.apply {
            assertEquals(
                expected = maxPoint - damage,
                actual = point
            )
        }
    }
}
