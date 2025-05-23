package gamescreen.battle.usecase.attack

import core.ModuleCore
import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import core.domain.status.param.ParameterType
import core.domain.status.param.StatusParameter
import core.domain.status.param.StatusParameterWithMax
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

class AttackUseCaseImplFromEnemyTest : KoinTest {
    private val attackUseCaseImplFromEnemy: AttackUseCase by inject(
        qualifier = named(QualifierAttackFromEnemy)
    )

    private val playerStatusRepository: PlayerStatusRepository by inject()

    private val hpValue = 50
    val hp = StatusParameterWithMax<ParameterType.HP>(
        point = hpValue,
        maxPoint = 100,
    )

    private val atk = 5
    private val statusData = TestActiveMonster.run {
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
            playerStatusRepository.setStatus(
                id = 0,
                status = testActivePlayer.copy(
                    statusData = testActivePlayer.statusData.copy(
                        hp = hp,
                    ),
                ),
            )

            attackUseCaseImplFromEnemy.invoke(
                target = 0,
                attacker = statusData.statusData,
            )

            playerStatusRepository.getStatus(0).apply {
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
            playerStatusRepository.setStatus(
                id = id,
                status = testActivePlayer.copy(
                    statusData = testActivePlayer.statusData.copy(
                        hp = hp,
                    ),
                ),
            )
            val damage = 5
            attackUseCaseImplFromEnemy.invoke(
                target = id,
                attacker = statusData.statusData,
            )

            playerStatusRepository.getStatus(id).apply {
                assertEquals(
                    expected = hpValue - damage,
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
            playerStatusRepository.setStatus(
                id = idNotActive,
                status = testActivePlayer.copy(
                    statusData = testActivePlayer.statusData.copy(
                        hp = StatusParameterWithMax(
                            point = 0,
                            maxPoint = 100
                        ),
                    ),
                ),
            )
            playerStatusRepository.setStatus(
                id = idActive,
                status = testActivePlayer.copy(
                    statusData = testActivePlayer.statusData.copy(
                        hp = hp,
                    ),
                ),
            )

            attackUseCaseImplFromEnemy.invoke(
                target = idNotActive,
                attacker = statusData.statusData,
            )

            playerStatusRepository.getStatus(idActive).apply {
                assertEquals(
                    expected = hpValue - atk,
                    actual = this.statusData.hp.point
                )
            }
        }
    }
}
