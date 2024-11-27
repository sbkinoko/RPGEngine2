package gamescreen.battle.usecase.getcontrollerbyscreentype

import gamescreen.battle.BattleModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class GetControllerByCommandTypeUseCaseImplTest : KoinTest {
    private val getControllerByCommandTypeUseCase: GetControllerByCommandTypeUseCase by inject()

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
