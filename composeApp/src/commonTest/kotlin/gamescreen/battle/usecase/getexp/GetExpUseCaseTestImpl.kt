package gamescreen.battle.usecase.getexp

import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.monster.MonsterStatus
import core.repository.battlemonster.TestBattleInfoRepository
import org.koin.test.KoinTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetExpUseCaseTestImpl : KoinTest {
    private lateinit var getExpUseCase: GetExpUseCase

    /**
     * 入手経験値のテスト1
     */
    @Test
    fun getExpTest1() {

        val exp1 = 1
        val exp2 = 2

        val battleMonsterRepository = object : TestBattleInfoRepository {
            override fun getStatusList(): List<MonsterStatus> {
                return listOf(
                    TestActiveMonster.copy(
                        exp = exp1,
                    ),
                    TestActiveMonster.copy(
                        exp = exp2,
                    )
                )
            }
        }

        getExpUseCase = GetExpUseCaseImpl(
            battleInfoRepository = battleMonsterRepository,
        )

        val exp = getExpUseCase.invoke()

        assertEquals(
            expected = exp1 + exp2,
            actual = exp,
        )
    }

    @Test
    fun getExpTest2() {
        val exp1 = 2
        val exp2 = 5
        val exp3 = 7

        val battleMonsterRepository = object : TestBattleInfoRepository {
            override fun getStatusList(): List<MonsterStatus> {
                return listOf(
                    TestActiveMonster.copy(
                        exp = exp1,
                    ),
                    TestActiveMonster.copy(
                        exp = exp2,
                    ),
                    TestActiveMonster.copy(
                        exp = exp3,
                    )
                )
            }
        }

        getExpUseCase = GetExpUseCaseImpl(
            battleInfoRepository = battleMonsterRepository,
        )

        val exp = getExpUseCase.invoke()

        assertEquals(
            expected = exp1 + exp2 + exp3,
            actual = exp,
        )
    }
}
