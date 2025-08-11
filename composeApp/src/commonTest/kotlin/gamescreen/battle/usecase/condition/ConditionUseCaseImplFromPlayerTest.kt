package gamescreen.battle.usecase.condition

import core.EnemyStatusRepositoryName
import core.ModuleCore
import core.domain.status.ConditionType
import core.domain.status.StatusDataTest
import core.repository.character.statusdata.StatusDataRepository
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


class ConditionUseCaseImplFromPlayerTest : KoinTest {
    private val conditionUseCase by inject<ConditionUseCase>(
        qualifier = named(
            QualifierAttackFromPlayer
        )
    )

    private val enemyStatusDataRepository: StatusDataRepository by inject(
        qualifier = EnemyStatusRepositoryName,
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
            val target = 0
            enemyStatusDataRepository.setStatusList(
                listOf(
                    StatusDataTest.TestEnemyStatusActive,
                )
            )
            conditionUseCase.invoke(
                target = target,
                conditionType = ConditionType.Poison()
            )

            enemyStatusDataRepository.getStatusData(target).apply {
                assertEquals(
                    expected = listOf(ConditionType.Poison()),
                    actual = conditionList
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
                    StatusDataTest.TestEnemyStatusActive,
                    StatusDataTest.TestEnemyStatusActive,
                )
            )
            conditionUseCase.invoke(
                target = id,
                conditionType = ConditionType.Poison()
            )

            enemyStatusDataRepository.getStatusData(id).apply {
                assertEquals(
                    expected = listOf(ConditionType.Poison()),
                    actual = conditionList
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
                    StatusDataTest.TestEnemyStatusActive,
                )
            )

            conditionUseCase.invoke(
                target = idNotActive,
                conditionType = ConditionType.Poison(),
            )

            enemyStatusDataRepository.getStatusData(idActive).apply {
                assertEquals(
                    expected = listOf(ConditionType.Poison()),
                    actual = conditionList
                )
            }
        }
    }
}
