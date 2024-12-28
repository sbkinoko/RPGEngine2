package gamescreen.battle.usecase.getdroptool

import core.ModuleCore
import core.repository.item.tool.ToolRepositoryImpl
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

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

        val id = getDropToolUseCase.invoke()

        assertEquals(
            expected = ToolRepositoryImpl.HEAL_TOOL,
            actual = id,
        )
    }
}
