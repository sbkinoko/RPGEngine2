package battle.serviceimpl

import common.status.MonsterStatusTest.Companion.getMonster
import core.CoreModule
import core.repository.battlemonster.BattleMonsterRepository
import core.usecase.updateparameter.UpdateMonsterStatusUseCase
import gamescreen.battle.BattleModule
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateMonsterParameterUseCaseTest : KoinTest {

    private val updateMonsterParameterUseCase: UpdateMonsterStatusUseCase by inject()
    private val battleMonsterRepository: BattleMonsterRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                CoreModule,
                BattleModule,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun attackTo1() {
        runBlocking {
            val monster = getMonster()
            battleMonsterRepository.setMonsters(
                listOf(
                    monster,
                )
            )

            val damage = 5

            updateMonsterParameterUseCase.decHP(
                amount = damage,
                id = 0,
            )

            battleMonsterRepository.getStatus(0).hp.apply {
                assertEquals(
                    expected = maxPoint - damage,
                    actual = point
                )
            }
        }
    }
}
