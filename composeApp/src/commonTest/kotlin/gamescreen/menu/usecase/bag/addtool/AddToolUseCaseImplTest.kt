package gamescreen.menu.usecase.bag.addtool

import data.item.tool.ToolId
import gamescreen.menu.ModuleMenu
import gamescreen.menu.repository.bag.BagRepository
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class AddToolUseCaseImplTest : KoinTest {
    private val bagRepository: BagRepository by inject()
    private val addToolUseCase: AddToolUseCase by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(ModuleMenu)
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun addTest() {
        val id1 = ToolId.HEAL1
        val num1 = 1

        (id1 to num1).apply {
            addToolUseCase.invoke(
                toolId = first,
                toolNum = second,
            )

            val list = bagRepository.getList()
            assertTrue(
                list.any {
                    it.id == first
                }
            )
            val data = list.first { it.id == first }

            assertTrue {
                data.num == second
            }
        }

        val id2 = ToolId.HEAL2
        val num2 = 1

        (id2 to num2).apply {
            addToolUseCase.invoke(
                toolId = first,
                toolNum = second,
            )

            val list = bagRepository.getList()
            assertTrue(
                list.any {
                    it.id == first
                }
            )
            val data = list.first { it.id == first }

            assertTrue {
                data.num == second
            }
        }

        val num3 = 1

        (id1 to num3).apply {
            addToolUseCase.invoke(
                toolId = first,
                toolNum = second,
            )

            val list = bagRepository.getList()
            assertTrue(
                list.any { it.id == first }
            )
            val data = list.first { it.id == first }

            assertTrue {
                data.num == 2 * num1
            }
        }
    }
}
