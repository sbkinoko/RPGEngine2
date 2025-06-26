package gamescreen.menu.repository.bag

import core.ModuleCore
import core.ToolBagRepositoryName
import core.domain.item.BagItemData
import core.repository.bag.BagRepository
import data.item.tool.ToolId
import gamescreen.menu.ModuleMenu
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BagRepositoryImplTest : KoinTest {
    private val bagRepository: BagRepository<ToolId> by inject(
        qualifier = ToolBagRepositoryName,
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
    fun setTest() {
        val first = ToolId.HEAL1
        val data1 = BagItemData(first, 1)
        bagRepository.setData(data1)
        assertEquals(
            actual = bagRepository.getList(),
            expected = listOf(data1)
        )
        assertEquals(
            actual = bagRepository.getItemIdAt(0),
            expected = first
        )

        val second = ToolId.HEAL2
        val data2 = BagItemData(second, 2)
        bagRepository.setData(data2)
        assertEquals(
            actual = bagRepository.getList(),
            expected = listOf(data1, data2),
        )
        assertEquals(
            actual = bagRepository.getItemIdAt(1),
            expected = second
        )

        val data3 = BagItemData(first, 3)
        bagRepository.setData(data3)
        assertEquals(
            actual = bagRepository.getList(),
            expected = listOf(data3, data2),
        )
    }
}
