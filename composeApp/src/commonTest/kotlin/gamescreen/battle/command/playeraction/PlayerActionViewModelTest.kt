package gamescreen.battle.command.playeraction

import gamescreen.battle.ModuleBattle
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class PlayerActionViewModelTest : KoinTest {
    private val viewModel = PlayerActionViewModel()

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
