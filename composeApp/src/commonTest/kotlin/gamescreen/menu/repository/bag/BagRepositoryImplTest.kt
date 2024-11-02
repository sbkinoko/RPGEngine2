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
        val data1 = BagToolData(1, 1)
        bagRepository.setData(data1)
        assertEquals(
            actual = bagRepository.getList(),
            expected = listOf(data1)
        )

        val data2 = BagToolData(2, 2)
        bagRepository.setData(data2)
        assertEquals(
            actual = bagRepository.getList(),
            expected = listOf(data1, data2),
        )

        val data3 = BagToolData(1, 3)
        bagRepository.setData(data3)
        assertEquals(
            actual = bagRepository.getList(),
            expected = listOf(data3, data2),
        )
    }
}
