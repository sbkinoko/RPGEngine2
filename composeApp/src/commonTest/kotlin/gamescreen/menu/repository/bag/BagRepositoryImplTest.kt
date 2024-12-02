package gamescreen.menu.repository.bag

import gamescreen.menu.MenuModule
import gamescreen.menu.domain.BagToolData
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BagRepositoryImplTest : KoinTest {
    private val bagRepository: BagRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(MenuModule)
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun setTest() {
        val first = 1
        val data1 = BagToolData(first, 1)
        bagRepository.setData(data1)
        assertEquals(
            actual = bagRepository.getList(),
            expected = listOf(data1)
        )
        assertEquals(
            actual = bagRepository.getItemIdAt(0),
            expected = first
        )

        val second = 2
        val data2 = BagToolData(second, 2)
        bagRepository.setData(data2)
        assertEquals(
            actual = bagRepository.getList(),
            expected = listOf(data1, data2),
        )
        assertEquals(
            actual = bagRepository.getItemIdAt(1),
            expected = second
        )

        val data3 = BagToolData(1, 3)
        bagRepository.setData(data3)
        assertEquals(
            actual = bagRepository.getList(),
            expected = listOf(data3, data2),
        )
    }
}
