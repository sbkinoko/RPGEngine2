package gamescreen.battle.usecase.attack

import gamescreen.battle.BattleModule
import gamescreen.battle.QualifierAttackFromPlayer
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest


class AttackUseCaseImplFromPlayerTest : KoinTest {
    private val attackUseCase: AttackUseCase by inject<AttackUseCase>(
        qualifier = named(
            QualifierAttackFromPlayer
        )
    )

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                BattleModule,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }
}
