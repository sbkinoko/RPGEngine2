package gamescreen.battle.usecase.getmoney

import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.monster.MonsterStatus
import core.repository.battlemonster.TestBattleInfoRepository
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

        val battleMonsterRepository = object : TestBattleInfoRepository {
            override fun getMonsters(): List<MonsterStatus> {
                return listOf(
                    TestActiveMonster.copy(
                        money = money1,
                    ),
                    TestActiveMonster.copy(
                        money = money2,
                    )
                )
            }

            override fun getStatusList(): List<MonsterStatus> {
                return listOf(
                    TestActiveMonster.copy(
                        money = money1,
                    ),
                    TestActiveMonster.copy(
                        money = money2,
                    )
                )
            }
        }

        getMoneyUseCase = GetMoneyUseCaseImpl(
            battleInfoRepository = battleMonsterRepository,
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

        val battleMonsterRepository = object : TestBattleInfoRepository {
            override fun getMonsters(): List<MonsterStatus> {
                return listOf(
                    TestActiveMonster.copy(
                        money = money1,
                    ),
                    TestActiveMonster.copy(
                        money = money2,
                    ),
                    TestActiveMonster.copy(
                        money = money3,
                    )
                )
            }

            override fun getStatusList(): List<MonsterStatus> {
                return listOf(
                    TestActiveMonster.copy(
                        money = money1,
                    ),
                    TestActiveMonster.copy(
                        money = money2,
                    ),
                    TestActiveMonster.copy(
                        money = money3,
                    )
                )
            }
        }

        getMoneyUseCase = GetMoneyUseCaseImpl(
            battleInfoRepository = battleMonsterRepository,
        )

        val money = getMoneyUseCase.invoke()

        assertEquals(
            expected = money1 + money2 + money3,
            actual = money,
        )
    }
}
