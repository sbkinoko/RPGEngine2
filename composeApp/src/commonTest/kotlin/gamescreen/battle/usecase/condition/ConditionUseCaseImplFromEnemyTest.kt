package gamescreen.battle.usecase.condition

import core.ModuleCore
import core.domain.status.ConditionType
import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import core.domain.status.PlayerStatusTest.Companion.testNotActivePlayer
import core.domain.status.StatusType
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

class ConditionUseCaseImplFromEnemyTest : KoinTest {
    private val conditionUseCaseImplFromEnemy: ConditionUseCase by inject(
        qualifier = named(QualifierAttackFromEnemy)
    )

    private val statusRepository: StatusRepository by inject()
    private val statusDataRepository: StatusDataRepository<StatusType.Player> by inject()

    private val conditionType = ConditionType.Poison()
    private val expectedList = listOf(conditionType)

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
                statusData = testActivePlayer.statusData,
            )

            conditionUseCaseImplFromEnemy.invoke(
                target = 0,
                conditionType = conditionType,
            )

            statusDataRepository.getStatusData(0).apply {
                assertEquals(
                    expected = expectedList,
                    actual = conditionList,
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
                statusData = testActivePlayer.statusData,
            )
            conditionUseCaseImplFromEnemy.invoke(
                target = id,
                conditionType = conditionType,
            )

            statusDataRepository.getStatusData(id).apply {
                assertEquals(
                    expected = expectedList,
                    actual = conditionList,
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
                statusData = testNotActivePlayer.statusData
            )
            statusDataRepository.setStatusData(
                id = idActive,
                statusData = testActivePlayer.statusData,
            )

            conditionUseCaseImplFromEnemy.invoke(
                target = idNotActive,
                conditionType = conditionType,
            )

            statusDataRepository.getStatusData(idActive).apply {
                assertEquals(
                    expected = expectedList,
                    actual = conditionList
                )
            }
        }
    }
}
