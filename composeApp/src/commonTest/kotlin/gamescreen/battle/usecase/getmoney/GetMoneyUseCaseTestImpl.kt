package gamescreen.battle.usecase.getmoney

import common.status.MonsterStatusTest.Companion.getTestMonster
import core.domain.status.MonsterStatus
import core.repository.battlemonster.TestBattleMonsterRepository
import org.koin.test.KoinTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetMoneyUseCaseTestImpl : KoinTest {
    private lateinit var getMoneyUseCase: GetMoneyUseCase

    /**
     * 入手金額のテスト1
     */
    @Test
    fun getMoneyTest1() {
        val money1 = 1
        val money2 = 2

        val battleMonsterRepository = object : TestBattleMonsterRepository {
            override fun getMonsters(): List<MonsterStatus> {
                return listOf(
                    getTestMonster().copy(
                        money = money1,
                    ),
                    getTestMonster().copy(
                        money = money2,
                    )
                )
            }
        }

        getMoneyUseCase = GetMoneyUseCaseImpl(
            battleMonsterRepository = battleMonsterRepository,
        )

        val money = getMoneyUseCase.invoke()

        assertEquals(
            expected = money1 + money2,
            actual = money,
        )
    }

    /**
     * 入手金額のテスト2
     */
    @Test
    fun getMoneyTest2() {
        val money1 = 2
        val money2 = 5
        val money3 = 7

        val battleMonsterRepository = object : TestBattleMonsterRepository {
            override fun getMonsters(): List<MonsterStatus> {
                return listOf(
                    getTestMonster().copy(
                        money = money1,
                    ),
                    getTestMonster().copy(
                        money = money2,
                    ),
                    getTestMonster().copy(
                        money = money3,
                    )
                )
            }
        }

        getMoneyUseCase = GetMoneyUseCaseImpl(
            battleMonsterRepository = battleMonsterRepository,
        )

        val money = getMoneyUseCase.invoke()

        assertEquals(
            expected = money1 + money2 + money3,
            actual = money,
        )
    }
}
