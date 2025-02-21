package gamescreen.battle.usecase.attack

import core.ModuleCore
import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.MonsterStatusTest.Companion.TestNotActiveMonster
import core.domain.status.param.HP
import core.repository.battlemonster.BattleInfoRepository
import data.ModuleData
import gamescreen.battle.ModuleBattle
import gamescreen.battle.QualifierAttackFromPlayer
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class AttackUseCaseImplFromPlayerTest : KoinTest {
    private val attackUseCase: AttackUseCase by inject<AttackUseCase>(
        qualifier = named(
            QualifierAttackFromPlayer
        )
    )

    private val hpValue = 50
    val hp = HP(
        value = hpValue,
        maxValue = 100
    )

    private val battleInfoRepository: BattleInfoRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleBattle,
                ModuleCore,
                ModuleData,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun toActive() {
        runBlocking {
            battleInfoRepository.setMonsters(
                listOf(
                    TestActiveMonster.copy(
                        hp = hp
                    ),
                ),
            )
            val damage = 5
            attackUseCase.invoke(
                target = 0,
                damage = damage,
            )

            battleInfoRepository.getStatus(0).apply {
                assertEquals(
                    expected = hpValue - damage,
                    actual = this.hp.value
                )
            }
        }
    }

    @Test
    fun toActive2() {
        val id = 1
        runBlocking {
            battleInfoRepository.setMonsters(
                listOf(
                    TestActiveMonster.copy(
                        hp = hp
                    ),
                    TestActiveMonster.copy(
                        hp = hp
                    ),
                ),
            )
            val damage = 5
            attackUseCase.invoke(
                target = id,
                damage = damage,
            )

            battleInfoRepository.getStatus(id).apply {
                assertEquals(
                    expected = hpValue - damage,
                    actual = this.hp.value
                )
            }
        }
    }

    @Test
    fun toNotActive2() {
        val idNotActive = 0
        val idActive = 1
        runBlocking {
            battleInfoRepository.setMonsters(
                listOf(
                    TestNotActiveMonster,
                    TestActiveMonster.copy(
                        hp = hp
                    ),
                ),
            )
            val damage = 5
            attackUseCase.invoke(
                target = idNotActive,
                damage = damage,
            )

            battleInfoRepository.getStatus(idActive).apply {
                assertEquals(
                    expected = hpValue - damage,
                    actual = this.hp.value
                )
            }
        }
    }
}
