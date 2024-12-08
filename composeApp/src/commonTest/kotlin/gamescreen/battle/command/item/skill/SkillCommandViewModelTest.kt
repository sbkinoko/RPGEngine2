package gamescreen.battle.command.item.skill

import gamescreen.battle.ModuleBattle
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class SkillCommandViewModelTest : KoinTest {

    private val viewModel = SkillCommandViewModel()

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
