package gamescreen.text

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class TextViewModelTest : KoinTest {
    private val textViewModel = TextViewModel()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(TextModule)
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }
}
