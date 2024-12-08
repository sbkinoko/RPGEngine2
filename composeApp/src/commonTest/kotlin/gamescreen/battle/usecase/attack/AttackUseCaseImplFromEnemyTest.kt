package gamescreen.battle.usecase.attack

import gamescreen.battle.ModuleBattle
import gamescreen.battle.QualifierAttackFromEnemy
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class AttackUseCaseImplFromEnemyTest : KoinTest {
    private val attackUseCaseImplFromEnemy: AttackUseCase by inject(
        qualifier = named(QualifierAttackFromEnemy)
    )

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleBattle,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }
}
