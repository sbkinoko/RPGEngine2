package core.usecase.updateparameter

import core.ModuleCore
import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.repository.battlemonster.BattleMonsterRepository
import gamescreen.battle.ModuleBattle
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
                ModuleCore,
                ModuleBattle,
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
            val monster = TestActiveMonster
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
