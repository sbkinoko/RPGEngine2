package gamescreen.battle.usecase.condition

import core.ModuleCore
import core.domain.status.ConditionType
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


class ConditionUseCaseImplFromPlayerTest : KoinTest {
    private val conditionUseCase by inject<ConditionUseCase>(
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
            val target = 0
            battleInfoRepository.setMonsters(
                listOf(
                    TestActiveMonster.copy(
                        hp = hp
                    ),
                ),
            )
            conditionUseCase.invoke(
                target = target,
                conditionType = ConditionType.Poison()
            )

            battleInfoRepository.getStatus(target).apply {
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
            conditionUseCase.invoke(
                target = id,
                conditionType = ConditionType.Poison()
            )

            battleInfoRepository.getStatus(id).apply {
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
            battleInfoRepository.setMonsters(
                listOf(
                    TestNotActiveMonster,
                    TestActiveMonster.copy(
                        hp = hp
                    ),
                ),
            )

            conditionUseCase.invoke(
                target = idNotActive,
                conditionType = ConditionType.Poison(),
            )

            battleInfoRepository.getStatus(idActive).apply {
                assertEquals(
                    expected = listOf(ConditionType.Poison()),
                    actual = conditionList
                )
            }
        }
    }
}
