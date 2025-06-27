package gamescreen.battle.usecase.attack

import core.EnemyStatusRepositoryName
import core.ModuleCore
import core.domain.item.DamageType
import core.domain.status.StatusDataTest
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
    private val attackUseCase: AttackUseCase by inject(
        qualifier = named(
            QualifierAttackFromPlayer
        )
    )

    private val hpValue = 50
    val hp = StatusParameterWithMax<ParameterType.HP>(
        point = hpValue,
        maxPoint = 100
    )

    private val enemyStatusDataRepository: StatusDataRepository by inject(
        qualifier = EnemyStatusRepositoryName,
    )

    private val atkValue = 5
    private val statusData = StatusDataTest.TestPlayerStatusActive.copy(
        atk = StatusParameter(atkValue)
    )

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
                    StatusDataTest.TestEnemyStatusActive.copy(
                        hp = hp
                    ),
                ),
            )

            attackUseCase.invoke(
                target = 0,
                attacker = statusData,
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
                    StatusDataTest.TestEnemyStatusInActive.copy(
                        hp = hp,
                    ),
                    StatusDataTest.TestEnemyStatusInActive.copy(
                        hp = hp,
                    ),
                )
            )

            attackUseCase.invoke(
                target = id,
                attacker = statusData,
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
                    StatusDataTest.TestEnemyStatusInActive,
                    StatusDataTest.TestEnemyStatusActive.copy(
                        hp = hp,
                    ),
                )
            )

            attackUseCase.invoke(
                target = idNotActive,
                attacker = statusData,
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
