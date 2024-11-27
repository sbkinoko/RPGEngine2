package gamescreen.choice

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class ChoiceViewModelTest : KoinTest {
    private val choiceViewModel = ChoiceViewModel()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(ChoiceModule)
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }
}
