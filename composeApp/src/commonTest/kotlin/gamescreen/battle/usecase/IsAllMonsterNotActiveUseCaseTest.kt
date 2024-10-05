package battle.usecase

import common.status.MonsterStatusTest.Companion.getMonster
import core.CoreModule
import core.repository.battlemonster.BattleMonsterRepository
import gamescreen.battle.BattleModule
import gamescreen.battle.QualifierAttackFromPlayer
import gamescreen.battle.usecase.IsAllMonsterNotActiveUseCase
import gamescreen.battle.usecase.attack.AttackUseCase
import kotlinx.coroutines.delay
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

class IsAllMonsterNotActiveUseCaseTest : KoinTest {
    private val repository: BattleMonsterRepository by inject()

    private val attackUseCase: AttackUseCase by inject(
        qualifier = named(QualifierAttackFromPlayer)
    )
    private val isAllMonsterNotActiveUseCase: IsAllMonsterNotActiveUseCase
            by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                BattleModule,
                CoreModule,
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
            repository.setMonsters(
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
