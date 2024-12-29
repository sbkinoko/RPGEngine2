package gamescreen.map.usecase.battledecidemonster

import common.status.MonsterStatusTest.Companion.getTestMonster
import core.domain.status.MonsterStatus
import data.monster.MonsterRepository
import kotlin.test.Test
import kotlin.test.assertTrue

class DecideBattleMonsterUseCaseImplTest {
    private lateinit var monsterRepository: MonsterRepository

    @Test
    fun count() {
        monsterRepository = object : MonsterRepository {
            override fun getMonster(id: Int): MonsterStatus {
                return getTestMonster()
            }
        }

        val useCase = DecideBattleMonsterUseCaseImpl(
            monsterRepository = monsterRepository,
        )

        // 回数を確保してテスト
        val list = List(50) {
            useCase.invoke()
        }

        for (size: Int in 1..5) {
            assertTrue(
                list.any {
                    it.size == size
                }
            )
        }
    }
}
