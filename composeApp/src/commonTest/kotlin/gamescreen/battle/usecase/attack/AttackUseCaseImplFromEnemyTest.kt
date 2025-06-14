package gamescreen.battle.usecase.attack

import core.ModuleCore
import core.PlayerStatusRepositoryName
import core.domain.item.DamageType
import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import core.domain.status.StatusType
import core.domain.status.param.ParameterType
import core.domain.status.param.StatusParameter
import core.domain.status.param.StatusParameterWithMax
import core.repository.statusdata.StatusDataRepository
import data.ModuleData
import data.status.StatusRepository
import gamescreen.battle.ModuleBattle
import gamescreen.battle.QualifierAttackFromEnemy
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject
import values.Constants
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AttackUseCaseImplFromEnemyTest : KoinTest {
    private val attackUseCaseImplFromEnemy: AttackUseCase<StatusType.Enemy> by inject(
        qualifier = named(QualifierAttackFromEnemy)
    )

    private val statusRepository: StatusRepository by inject()
    private val statusDataRepository: StatusDataRepository<StatusType.Player> by inject(
        qualifier = PlayerStatusRepositoryName
    )

    private val hpValue = 50
    val hp = StatusParameterWithMax<ParameterType.HP>(
        point = hpValue,
        maxPoint = 100,
    )

    private val atkValue = 5
    private val statusData = TestActiveMonster.run {
        copy(
            this.statusData.copy(
                atk = StatusParameter(atkValue),
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

        statusDataRepository.setStatusList(
            List(Constants.playerNum) {
                statusRepository.getStatus(
                    id = it,
                    level = 1,
                ).statusData
            }
        )
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun toActive() {
        runBlocking {
            statusDataRepository.setStatusData(
                id = 0,
                statusData = testActivePlayer.statusData.copy(
                    hp = hp,
                ),
            )

            attackUseCaseImplFromEnemy.invoke(
                target = 0,
                attacker = statusData.statusData,
                damageType = DamageType.AtkMultiple(1),
            )

            statusDataRepository.getStatusData(0).apply {
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
            statusDataRepository.setStatusData(
                id = id,
                statusData = testActivePlayer.statusData.copy(
                    hp = hp,
                ),
            )

            val damage = 5
            attackUseCaseImplFromEnemy.invoke(
                target = id,
                attacker = statusData.statusData,
                damageType = DamageType.AtkMultiple(1),
            )

            statusDataRepository.getStatusData(id).apply {
                assertEquals(
                    expected = hpValue - damage,
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
            statusDataRepository.setStatusData(
                id = idNotActive,
                statusData = testActivePlayer.statusData.copy(
                    hp = StatusParameterWithMax(
                        point = 0,
                        maxPoint = 100
                    ),
                ),
            )
            statusDataRepository.setStatusData(
                id = idActive,
                statusData = testActivePlayer.statusData.copy(
                    hp = hp,
                ),
            )

            attackUseCaseImplFromEnemy.invoke(
                target = idNotActive,
                attacker = statusData.statusData,
                damageType = DamageType.AtkMultiple(1),
            )

            statusDataRepository.getStatusData(idActive).apply {
                assertEquals(
                    expected = hpValue - atkValue,
                    actual = hp.point
                )
            }
        }
    }
}
