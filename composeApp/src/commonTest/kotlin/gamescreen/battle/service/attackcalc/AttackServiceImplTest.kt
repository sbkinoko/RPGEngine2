package gamescreen.battle.service.attackcalc

import core.domain.item.DamageType
import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import core.domain.status.StatusData
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
        val def = 3
        val rate = 1
        val maxPoint = 20

        val updated = attackMulti(
            atk = atk,
            def = def,
            rate = rate,
            maxPoint = maxPoint
        )

        assertEquals(
            expected = maxPoint - atk * rate + def,
            actual = updated.hp.point
        )
    }

    /**
     * 単純な引き算2
     */
    @Test
    fun attack2() {
        val atk = 11
        val def = 7
        val rate = 1
        val maxPoint = 40

        val updated = attackMulti(
            atk = atk,
            def = def,
            rate = rate,
            maxPoint = maxPoint
        )

        assertEquals(
            expected = maxPoint - atk * rate + def,
            actual = updated.hp.point
        )
    }

    /**
     * 攻撃力を乗じてから引き算1
     */
    @Test
    fun attackMulti1() {
        val atk = 11
        val def = 7
        val rate = 2
        val maxPoint = 40

        val updated = attackMulti(
            atk = atk,
            def = def,
            rate = rate,
            maxPoint = maxPoint
        )

        assertEquals(
            expected = maxPoint - atk * rate + def,
            actual = updated.hp.point
        )
    }

    /**
     * 攻撃力を乗じてから引き算2
     */
    @Test
    fun attackMulti2() {
        val atk = 10
        val def = 3
        val rate = 3
        val maxPoint = 50

        val updated = attackMulti(
            atk = atk,
            def = def,
            rate = rate,
            maxPoint = maxPoint
        )

        assertEquals(
            expected = maxPoint - atk * rate + def,
            actual = updated.hp.point
        )
    }

    /**
     * 防御の方が高いのでダメージなし1
     */
    @Test
    fun attackGreaterDef() {
        val atk = 11
        val def = 20
        val maxPoint = 50
        val rate = 1

        val updated = attackMulti(
            atk = atk,
            def = def,
            maxPoint = maxPoint,
            rate = rate,
        )

        assertEquals(
            expected = maxPoint - 1,
            actual = updated.hp.point
        )
    }

    /**
     * 防御の方が高いのでダメージなし2
     */
    @Test
    fun attackGreaterDef2() {
        val atk = 11
        val def = 30
        val maxPoint = 50
        val rate = 2

        val updated = attackMulti(
            atk = atk,
            def = def,
            maxPoint = maxPoint,
            rate = rate,
        )

        assertEquals(
            expected = maxPoint - 1,
            actual = updated.hp.point
        )
    }

    /**
     * 倍率をのせた攻撃のテスト
     */
    private fun attackMulti(
        atk: Int,
        def: Int,
        maxPoint: Int,
        rate: Int,
    ): StatusData {
        val attacker = testActivePlayer.run {
            copy(
                statusData = statusData.copy(
                    atk = StatusParameter(atk),
                )
            )
        }

        val attacked = TestActiveMonster.run {
            copy(
                statusData = statusData.copy(
                    def = StatusParameter(def),
                    hp = StatusParameterWithMax(
                        maxPoint = maxPoint,
                    )
                )
            )
        }

        return attackCalcService.invoke(
            attacker = attacker.statusData,
            attacked = attacked.statusData,
            damageType = DamageType.AtkMultiple(rate),
        )
    }

    /**
     * 割合ダメージ確認1
     */
    @Test
    fun rateAttack1() {
        val maxPoint = 50
        val rate = 20

        val updated = rateAttack(
            maxPoint = maxPoint,
            rate = rate,
        )
        assertEquals(
            expected = maxPoint * (100 - rate) / 100,
            actual = updated.hp.point
        )
    }

    /**
     * 割合ダメージ確認2
     */
    @Test
    fun rateAttack2() {
        val maxPoint = 80
        val rate = 25

        val updated = rateAttack(
            maxPoint = maxPoint,
            rate = rate,
        )

        assertEquals(
            expected = maxPoint * (100 - rate) / 100,
            actual = updated.hp.point
        )
    }

    /**
     * hpを入力して割合ダメージ処理を呼び出し
     */
    private fun rateAttack(
        maxPoint: Int,
        rate: Int,
    ): StatusData {
        val attacker = testActivePlayer

        val attacked = TestActiveMonster.run {
            copy(
                statusData = statusData.copy(
                    hp = StatusParameterWithMax(
                        maxPoint = maxPoint,
                    )
                )
            )
        }

        return attackCalcService.invoke(
            attacker = attacker.statusData,
            attacked = attacked.statusData,
            damageType = DamageType.Rate(rate),
        )
    }

    /**
     * 固定ダメージ確認1
     */
    @Test
    fun fixAttack1() {
        val maxPoint = 80
        val amount = 25

        val updated = fixDamage(
            maxPoint = maxPoint,
            amount = amount,
        )

        assertEquals(
            expected = maxPoint - amount,
            actual = updated.hp.point,
        )
    }

    /**
     * 固定ダメージ確認2
     */
    @Test
    fun fixAttack2() {
        val maxPoint = 70
        val amount = 60

        val updated = fixDamage(
            maxPoint = maxPoint,
            amount = amount,
        )

        assertEquals(
            expected = maxPoint - amount,
            actual = updated.hp.point,
        )
    }

    /**
     * hpを入力して固定ダメージ処理を呼び出し
     */
    private fun fixDamage(
        maxPoint: Int,
        amount: Int,
    ): StatusData {
        val attacker = testActivePlayer

        val attacked = TestActiveMonster.run {
            copy(
                statusData = statusData.copy(
                    hp = StatusParameterWithMax(
                        maxPoint = maxPoint,
                    )
                )
            )
        }

        return attackCalcService.invoke(
            attacker = attacker.statusData,
            attacked = attacked.statusData,
            damageType = DamageType.Fixed(amount),
        )
    }
}
