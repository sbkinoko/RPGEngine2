package gamescreen.map.usecase.battledecidemonster

import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.StatusData
import core.domain.status.StatusDataTest
import core.domain.status.monster.MonsterStatus
import data.monster.MonsterRepository
import kotlin.test.Test

class DecideBattleMonsterUseCaseImplTest {
    private lateinit var monsterRepository: MonsterRepository

    @Test
    fun count() {
        monsterRepository = object : MonsterRepository {
            override fun getMonster(id: Int): Pair<MonsterStatus, StatusData> {
                return Pair(
                    TestActiveMonster,
                    StatusDataTest.TestEnemyStatusActive,
                )
            }
        }

        val useCase = DecideBattleMonsterUseCaseImpl(
            monsterRepository = monsterRepository,
        )

        // fixme 敵をランダムで出すようにしたらテスト作成
        //        // 回数を確保してテスト
        //        val list = List(50) {
        //            useCase.invoke()
        //        }
        //
        //        for (size: Int in 1..5) {
        //            assertTrue(
        //                list.any {
        //                    it.size == size
        //                }
        //            )
        //        }
    }
}
