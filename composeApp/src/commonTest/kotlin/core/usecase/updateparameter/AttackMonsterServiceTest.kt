package core.usecase.updateparameter

import core.ModuleCore
import core.domain.status.MonsterStatusTest.Companion.TestActiveMonster
import core.repository.battlemonster.BattleInfoRepository
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
    private val battleInfoRepository: BattleInfoRepository by inject()

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
            battleInfoRepository.setMonsters(
                listOf(
                    monster,
                )
            )

            val damage = 5

            updateMonsterParameterUseCase.decHP(
                amount = damage,
                id = 0,
            )

            battleInfoRepository.getStatus(0).hp.apply {
                assertEquals(
                    expected = maxPoint - damage,
                    actual = point
                )
            }
        }
    }
}
