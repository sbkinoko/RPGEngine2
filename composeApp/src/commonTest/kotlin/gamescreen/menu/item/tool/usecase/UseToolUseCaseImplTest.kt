package gamescreen.menu.item.tool.usecase

import core.CoreModule
import gamescreen.menu.MenuModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class UseToolUseCaseImplTest : KoinTest {
    lateinit var useToolUseCase: UseToolUseCase

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                CoreModule,
                MenuModule,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

}
