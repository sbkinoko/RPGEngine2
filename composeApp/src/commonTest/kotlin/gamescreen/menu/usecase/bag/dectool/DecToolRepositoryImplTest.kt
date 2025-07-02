package gamescreen.menu.usecase.bag.dectool

import core.ModuleCore
import core.ToolBagRepositoryName
import core.domain.item.BagItemData
import core.repository.bag.BagRepository
import data.item.tool.ToolId
import gamescreen.menu.DecToolUseCaseName
import gamescreen.menu.ModuleMenu
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DecItemUseCaseImplTest : KoinTest {
    private val bagRepository: BagRepository<ToolId> by inject(
        qualifier = ToolBagRepositoryName,
    )
    private val decToolUseCase: DecItemUseCase<ToolId> by inject(
        qualifier = DecToolUseCaseName
    )

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleCore,
                ModuleMenu,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun testDecTool() {
        val id = ToolId.HEAL1
        val num = 1
        val bagToolData = BagItemData(
            id = id,
            num = num,
        )
        bagRepository.setData(
            data = bagToolData,
        )

        decToolUseCase(
            itemId = id,
            itemNum = num,
        )

        assertEquals(
            expected = 0,
            actual = bagRepository.getList().find { it.id == id }?.num,
        )

        var count = 0
        try {
            decToolUseCase(
                itemId = id,
                itemNum = num,
            )
        } catch (e: IllegalStateException) {
            count++
        }

        assertEquals(
            expected = 1,
            actual = count,
        )

        val id2 = ToolId.HEAL2
        try {
            decToolUseCase(
                itemId = id2,
                itemNum = num,
            )
        } catch (e: IllegalStateException) {
            count++
        }

        assertEquals(
            expected = 2,
            actual = count,
        )
    }
}
