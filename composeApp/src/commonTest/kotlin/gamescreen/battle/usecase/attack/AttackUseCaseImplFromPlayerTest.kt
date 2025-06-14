package gamescreen.battle.usecase.attack

import core.EnemyStatusRepositoryName
import core.ModuleCore
import core.domain.item.DamageType
import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.MonsterStatusTest.Companion.TestNotActiveMonster
import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import core.domain.status.StatusType
import core.domain.status.param.ParameterType
import core.domain.status.param.StatusParameter
import core.domain.status.param.StatusParameterWithMax
import core.repository.statusdata.StatusDataRepository
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
    private val attackUseCase: AttackUseCase<StatusType.Player> by inject(
        qualifier = named(
            QualifierAttackFromPlayer
        )
    )

    private val hpValue = 50
    val hp = StatusParameterWithMax<ParameterType.HP>(
        point = hpValue,
        maxPoint = 100
    )

    private val enemyStatusDataRepository: StatusDataRepository<StatusType.Enemy> by inject(
        qualifier = EnemyStatusRepositoryName,
    )

    private val atkValue = 5
    private val statusData = testActivePlayer.run {
        copy(
            this.statusData.copy(
                atk = StatusParameter(atkValue)
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
            enemyStatusDataRepository.setStatusList(
                listOf(
                    TestActiveMonster.run {
                        copy(
                            statusData = statusData.copy(
                                hp = hp
                            ),
                        )
                    }.statusData
                ),
            )

            attackUseCase.invoke(
                target = 0,
                attacker = statusData.statusData,
                damageType = DamageType.AtkMultiple(1),
            )

            enemyStatusDataRepository.getStatusData(0).apply {
                assertEquals(
                    expected = hpValue - atkValue,
                    actual = hp.point
                )
            }
        }
    }

    @Test
    fun toActive2() {
        val id = 1
        runBlocking {
            enemyStatusDataRepository.setStatusList(
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
                ).map {
                    it.statusData
                },
            )

            attackUseCase.invoke(
                target = id,
                attacker = statusData.statusData,
                damageType = DamageType.AtkMultiple(1),
            )

            enemyStatusDataRepository.getStatusData(id).apply {
                assertEquals(
                    expected = hpValue - atkValue,
                    actual = hp.point
                )
            }
        }
    }

    @Test
    fun toNotActive2() {
        val idNotActive = 0
        val idActive = 1
        runBlocking {
            enemyStatusDataRepository.setStatusList(
                listOf(
                    TestNotActiveMonster,
                    TestActiveMonster.run {
                        copy(
                            statusData = statusData.copy(
                                hp = hp,
                            ),
                        )
                    },
                ).map {
                    it.statusData
                },
            )

            attackUseCase.invoke(
                target = idNotActive,
                attacker = statusData.statusData,
                damageType = DamageType.AtkMultiple(1),
            )

            enemyStatusDataRepository.getStatusData(idActive).apply {
                assertEquals(
                    expected = hpValue - atkValue,
                    actual = hp.point
                )
            }
        }
    }
}
