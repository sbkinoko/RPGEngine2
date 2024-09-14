package battle.serviceimpl

import battle.service.attack.UpdateParameterService
import common.status.MonsterStatusTest.Companion.getMonster
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AttackMonsterServiceTest {

    private lateinit var attackMonsterService: UpdateParameterService

    @BeforeTest
    fun beforeTest() {
        attackMonsterService = UpdateParameterService()
    }

    @Test
    fun attackTo1() {
        val monster = getMonster()

        val id = 0
        val damage = 5

        val newMonsters = attackMonsterService.attack(
            target = id,
            damage = damage,
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
