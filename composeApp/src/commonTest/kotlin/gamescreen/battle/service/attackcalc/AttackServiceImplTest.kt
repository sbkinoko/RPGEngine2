package gamescreen.battle.service.attackcalc

import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import core.domain.status.param.StatusParameter
import core.domain.status.param.StatusParameterWithMax
import gamescreen.battle.ModuleBattle
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AttackServiceImplTest : KoinTest {
    private val attackCalcService: AttackCalcService by inject()

    val hp = 50

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleBattle,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }


    /**
     * 単純な引き算1
     */
    @Test
    fun attack1() {
        val atk = 5
        val attacker = testActivePlayer.run {
            copy(
                statusData = statusData.copy(
                    atk = StatusParameter(atk),
                )
            )
        }

        val def = 3
        val attacked = TestActiveMonster.run {
            copy(
                statusData = statusData.copy(
                    def = StatusParameter(def),
                    hp = StatusParameterWithMax(
                        maxPoint = 50,
                    )
                )
            )
        }

        val updated = attackCalcService.invoke(
            attacker = attacker.statusData,
            attacked = attacked.statusData,
        )

        assertEquals(
            expected = hp - atk + def,
            actual = updated.hp.point
        )
    }

    /**
     * 単純な引き算2
     */
    @Test
    fun attack2() {
        val atk = 11
        val attacker = testActivePlayer.run {
            copy(
                statusData = statusData.copy(
                    atk = StatusParameter(atk),
                )
            )
        }

        val def = 6
        val attacked = TestActiveMonster.run {
            copy(
                statusData = statusData.copy(
                    def = StatusParameter(def),
                    hp = StatusParameterWithMax(
                        maxPoint = 50,
                    )
                )
            )
        }

        val updated = attackCalcService.invoke(
            attacker = attacker.statusData,
            attacked = attacked.statusData,
        )

        assertEquals(
            expected = hp - atk + def,
            actual = updated.hp.point
        )
    }

    /**
     * 防御の方が高いのでダメージなし
     */
    @Test
    fun attackGreaterDef() {
        val atk = 11
        val attacker = testActivePlayer.run {
            copy(
                statusData = statusData.copy(
                    atk = StatusParameter(atk),
                )
            )
        }

        val def = 20
        val attacked = TestActiveMonster.run {
            copy(
                statusData = statusData.copy(
                    def = StatusParameter(def),
                    hp = StatusParameterWithMax(
                        maxPoint = 50,
                    )
                )
            )
        }

        val updated = attackCalcService.invoke(
            attacker = attacker.statusData,
            attacked = attacked.statusData,
        )

        assertEquals(
            expected = hp - 1,
            actual = updated.hp.point
        )
    }
}
