package gamescreen.battle.usecase.attack

import core.ModuleCore
import core.domain.item.DamageType
import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.MonsterStatusTest.Companion.TestNotActiveMonster
import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import core.domain.status.param.ParameterType
import core.domain.status.param.StatusParameter
import core.domain.status.param.StatusParameterWithMax
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
    val hp = StatusParameterWithMax<ParameterType.HP>(
        point = hpValue,
        maxPoint = 100
    )

    private val battleInfoRepository: BattleInfoRepository by inject()

    private val atk = 5
    private val statusData = testActivePlayer.run {
        copy(
            this.statusData.copy(
                atk = StatusParameter(atk)
            )
        )
    }

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
                    TestActiveMonster.run {
                        copy(
                            statusData = statusData.copy(
                                hp = hp
                            ),
                        )
                    }
                ),
            )

            attackUseCase.invoke(
                target = 0,
                attacker = statusData.statusData,
                damageType = DamageType.AtkMultiple(1),
            )

            battleInfoRepository.getStatus(0).apply {
                assertEquals(
                    expected = hpValue - atk,
                    actual = this.statusData.hp.point
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
                    TestActiveMonster.run {
                        copy(
                            statusData = statusData.copy(
                                hp = hp,
                            ),
                        )
                    },
                    TestActiveMonster.run {
                        copy(
                            statusData = statusData.copy(
                                hp = hp,
                            ),
                        )
                    },
                ),
            )

            attackUseCase.invoke(
                target = id,
                attacker = statusData.statusData,
                damageType = DamageType.AtkMultiple(1),
            )

            battleInfoRepository.getStatus(id).apply {
                assertEquals(
                    expected = hpValue - atk,
                    actual = this.statusData.hp.point
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
                    TestActiveMonster.run {
                        copy(
                            statusData = statusData.copy(
                                hp = hp,
                            ),
                        )
                    },
                ),
            )

            attackUseCase.invoke(
                target = idNotActive,
                attacker = statusData.statusData,
                damageType = DamageType.AtkMultiple(1),
            )

            battleInfoRepository.getStatus(idActive).apply {
                assertEquals(
                    expected = hpValue - atk,
                    actual = this.statusData.hp.point
                )
            }
        }
    }
}
