package gamescreen.battle.command.item.tool

import gamescreen.battle.ModuleBattle
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.BeforeTest

class ToolCommandViewModelTest : KoinTest {
    private val viewModel = ToolCommandViewModel()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleBattle,
            )
        }
    }

    @BeforeTest
    fun afterTest() {
        stopKoin()
    }
}
