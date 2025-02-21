package gamescreen.battle.usecase.condition

import core.ModuleCore
import core.domain.status.ConditionType
import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import core.domain.status.param.HP
import core.repository.player.PlayerStatusRepository
import data.ModuleData
import gamescreen.battle.ModuleBattle
import gamescreen.battle.QualifierAttackFromEnemy
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

class ConditionUseCaseImplFromEnemyTest : KoinTest {
    private val conditionUseCaseImplFromEnemy: ConditionUseCase by inject(
        qualifier = named(QualifierAttackFromEnemy)
    )

    private val playerStatusRepository: PlayerStatusRepository by inject()

    private val hpValue = 50
    val hp = HP(
        value = hpValue,
        maxValue = 100
    )

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
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun toActive() {
        runBlocking {
            playerStatusRepository.setStatus(
                id = 0,
                status = testActivePlayer.copy(
                    hp = hp
                ),
            )

            conditionUseCaseImplFromEnemy.invoke(
                target = 0,
                conditionType = conditionType,
            )

            playerStatusRepository.getStatus(0).apply {
                assertEquals(
                    expected = expectedList,
                    actual = this.conditionList,
                )
            }
        }
    }

    @Test
    fun toActive2() {
        val id = 1
        runBlocking {
            playerStatusRepository.setStatus(
                id = id,
                status = testActivePlayer.copy(
                    hp = hp
                ),
            )
            conditionUseCaseImplFromEnemy.invoke(
                target = id,
                conditionType = conditionType,
            )

            playerStatusRepository.getStatus(id).apply {
                assertEquals(
                    expected = expectedList,
                    actual = this.conditionList,
                )
            }
        }
    }

    @Test
    fun toNotActive2() {
        val idNotActive = 0
        val idActive = 1
        runBlocking {
            playerStatusRepository.setStatus(
                id = idNotActive,
                status = testActivePlayer.copy(
                    hp = HP(
                        value = 0,
                        maxValue = 100
                    ),
                ),
            )
            playerStatusRepository.setStatus(
                id = idActive,
                status = testActivePlayer.copy(
                    hp = hp,
                ),
            )

            conditionUseCaseImplFromEnemy.invoke(
                target = idNotActive,
                conditionType = conditionType,
            )

            playerStatusRepository.getStatus(idActive).apply {
                assertEquals(
                    expected = expectedList,
                    actual = this.conditionList
                )
            }
        }
    }
}
