package gamescreen.battle.usecase.attack

import core.ModuleCore
import core.domain.status.PlayerStatusTest.Companion.testActivePlayer
import core.domain.status.param.statusParameterWithMax.HP
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
    val hp = HP(
        value = hpValue,
        maxValue = 100
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
            playerStatusRepository.setStatus(
                id = 0,
                status = testActivePlayer.copy(
                    statusData = testActivePlayer.statusData.copy(
                        hp = hp,
                    ),
                ),
            )
            val damage = 5
            attackUseCaseImplFromEnemy.invoke(
                target = 0,
                damage = damage,
            )

            playerStatusRepository.getStatus(0).apply {
                assertEquals(
                    expected = hpValue - damage,
                    actual = this.statusData.hp.value
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
                damage = damage,
            )

            playerStatusRepository.getStatus(id).apply {
                assertEquals(
                    expected = hpValue - damage,
                    actual = this.statusData.hp.value
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
                        hp = HP(
                            value = 0,
                            maxValue = 100
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

            playerStatusRepository
            val damage = 5
            attackUseCaseImplFromEnemy.invoke(
                target = idNotActive,
                damage = damage,
            )

            playerStatusRepository.getStatus(idActive).apply {
                assertEquals(
                    expected = hpValue - damage,
                    actual = this.statusData.hp.value
                )
            }
        }
    }
}
