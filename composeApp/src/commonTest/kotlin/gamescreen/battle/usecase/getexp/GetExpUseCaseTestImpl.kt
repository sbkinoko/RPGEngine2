package gamescreen.battle.usecase.getexp

import common.status.MonsterStatusTest.Companion.getTestMonster
import core.domain.status.MonsterStatus
import core.repository.battlemonster.TestBattleMonsterRepository
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

        val battleMonsterRepository = object : TestBattleMonsterRepository {
            override fun getMonsters(): List<MonsterStatus> {
                return listOf(
                    getTestMonster().copy(
                        exp = exp1,
                    ),
                    getTestMonster().copy(
                        exp = exp2,
                    )
                )
            }
        }

        getExpUseCase = GetExpUseCaseImpl(
            battleMonsterRepository = battleMonsterRepository,
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

        val battleMonsterRepository = object : TestBattleMonsterRepository {
            override fun getMonsters(): List<MonsterStatus> {
                return listOf(
                    getTestMonster().copy(
                        exp = exp1,
                    ),
                    getTestMonster().copy(
                        exp = exp2,
                    ),
                    getTestMonster().copy(
                        exp = exp3,
                    )
                )
            }
        }

        getExpUseCase = GetExpUseCaseImpl(
            battleMonsterRepository = battleMonsterRepository,
        )

        val exp = getExpUseCase.invoke()

        assertEquals(
            expected = exp1 + exp2 + exp3,
            actual = exp,
        )
    }
}