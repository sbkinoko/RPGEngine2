package battle.usecase

import battle.BattleModule
import battle.repository.battlemonster.BattleMonsterRepository
import common.CommonModule
import common.status.MonsterStatusTest.Companion.getMonster
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class IsAllMonsterNotActiveUseCaseTest : KoinTest {
    private val repository: BattleMonsterRepository by inject()

    private val attackUseCase: AttackUseCase by inject()
    private val isAllMonsterNotActiveUseCase: IsAllMonsterNotActiveUseCase
            by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                BattleModule,
                CommonModule,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun checkIsBattleFinish() {
        runBlocking {
            repository.setMonster(
                MutableList(3) {
                    getMonster()
                }
            )

            delay(100)

            val id = 0
            val damage = 10

            attackUseCase(
                target = id,
                damage = damage
            )
            assertEquals(
                expected = false,
                actual = isAllMonsterNotActiveUseCase()
            )

            // 隣を攻撃
            attackUseCase(
                target = id,
                damage = damage
            )
            assertEquals(
                expected = false,
                actual = isAllMonsterNotActiveUseCase()
            )

            // 隣の隣を攻撃
            attackUseCase(
                target = id,
                damage = damage
            )

            assertEquals(
                expected = true,
                actual = isAllMonsterNotActiveUseCase()
            )
        }
    }
}
