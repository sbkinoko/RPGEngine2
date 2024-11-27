package gamescreen.battle.command.main

import gamescreen.battle.BattleModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class BattleMainViewModelTest : KoinTest {
    private val viewModel = BattleMainViewModel()

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
