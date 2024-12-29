package gamescreen.battle.usecase.getdroptool

import core.ModuleCore
import data.item.tool.ToolRepositoryImpl
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class GetDropToolUseCaseImplTest {

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleCore,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun getDropTool() {
        val getDropToolUseCase: GetDropToolUseCase = GetDropToolUseCaseImpl()

        val idList = List(10) {
            getDropToolUseCase.invoke()
        }

        assertTrue(
            actual = idList.contains(ToolRepositoryImpl.HEAL_TOOL)
        )
        assertTrue(
            actual = idList.contains(null)
        )
    }
}
