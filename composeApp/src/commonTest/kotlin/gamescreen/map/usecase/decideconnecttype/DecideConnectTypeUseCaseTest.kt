package gamescreen.map.usecase.decideconnecttype

import gamescreen.map.MapModule
import gamescreen.map.domain.ConnectType
import gamescreen.map.usecase.decideconnectcype.DecideConnectTypeUseCase
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DecideConnectTypeUseCaseTest : KoinTest {

    private val useCase: DecideConnectTypeUseCase by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(MapModule)
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun type1() {
        val list: Array<Array<Any>> = arrayOf(
            arrayOf(0, 1, 0),
            arrayOf(0, 1, 0),
            arrayOf(0, 0, 0),
        )

        assertEquals(
            actual = ConnectType.Vertical,
            expected = useCase(list),
        )
    }

    @Test
    fun type2() {
        val list: Array<Array<Any>> = arrayOf(
            arrayOf(0, 0, 0),
            arrayOf(1, 1, 0),
            arrayOf(0, 0, 0),
        )

        assertEquals(
            actual = ConnectType.Horizontal,
            expected = useCase(list),
        )
    }

    @Test
    fun type3() {
        val list: Array<Array<Any>> = arrayOf(
            arrayOf(0, 1, 0),
            arrayOf(1, 1, 0),
            arrayOf(0, 0, 0),
        )

        assertEquals(
            actual = ConnectType.LeftTopUp,
            expected = useCase(list),
        )
    }

    @Test
    fun type4() {
        val list: Array<Array<Any>> = arrayOf(
            arrayOf(0, 0, 0),
            arrayOf(0, 1, 1),
            arrayOf(0, 0, 0),
        )

        assertEquals(
            actual = ConnectType.Horizontal,
            expected = useCase(list),
        )
    }

    @Test
    fun type5() {
        val list: Array<Array<Any>> = arrayOf(
            arrayOf(0, 1, 0),
            arrayOf(0, 1, 1),
            arrayOf(0, 0, 0),
        )

        assertEquals(
            actual = ConnectType.RightToUp,
            expected = useCase(list),
        )
    }

    @Test
    fun type6() {
        val list: Array<Array<Any>> = arrayOf(
            arrayOf(0, 0, 0),
            arrayOf(1, 1, 1),
            arrayOf(0, 0, 0),
        )

        assertEquals(
            actual = ConnectType.Horizontal,
            expected = useCase(list),
        )
    }

    @Test
    fun type7() {
        val list: Array<Array<Any>> = arrayOf(
            arrayOf(0, 1, 0),
            arrayOf(1, 1, 1),
            arrayOf(0, 0, 0),
        )

        assertEquals(
            actual = ConnectType.WithoutDown,
            expected = useCase(list),
        )
    }

    @Test
    fun type8() {
        val list: Array<Array<Any>> = arrayOf(
            arrayOf(0, 0, 0),
            arrayOf(0, 1, 0),
            arrayOf(0, 1, 0),
        )

        assertEquals(
            actual = ConnectType.Vertical,
            expected = useCase(list),
        )
    }

    @Test
    fun type9() {
        val list: Array<Array<Any>> = arrayOf(
            arrayOf(0, 1, 0),
            arrayOf(0, 1, 0),
            arrayOf(0, 1, 0),
        )

        assertEquals(
            actual = ConnectType.Vertical,
            expected = useCase(list),
        )
    }

    @Test
    fun type10() {
        val list: Array<Array<Any>> = arrayOf(
            arrayOf(0, 0, 0),
            arrayOf(1, 1, 0),
            arrayOf(0, 1, 0),
        )

        assertEquals(
            actual = ConnectType.LeftTopDown,
            expected = useCase(list),
        )
    }

    @Test
    fun type11() {
        val list: Array<Array<Any>> = arrayOf(
            arrayOf(0, 1, 0),
            arrayOf(1, 1, 0),
            arrayOf(0, 1, 0),
        )

        assertEquals(
            actual = ConnectType.WithoutRight,
            expected = useCase(list),
        )
    }

    @Test
    fun type12() {
        val list: Array<Array<Any>> = arrayOf(
            arrayOf(0, 0, 0),
            arrayOf(0, 1, 1),
            arrayOf(0, 1, 0),
        )

        assertEquals(
            actual = ConnectType.RightToDown,
            expected = useCase(list),
        )
    }

    @Test
    fun type13() {
        val list: Array<Array<Any>> = arrayOf(
            arrayOf(0, 1, 0),
            arrayOf(0, 1, 1),
            arrayOf(0, 1, 0),
        )

        assertEquals(
            actual = ConnectType.WithoutLeft,
            expected = useCase(list),
        )
    }

    @Test
    fun type14() {
        val list: Array<Array<Any>> = arrayOf(
            arrayOf(0, 0, 0),
            arrayOf(1, 1, 1),
            arrayOf(0, 1, 0),
        )

        assertEquals(
            actual = ConnectType.WithoutUp,
            expected = useCase(list),
        )
    }

    @Test
    fun type15() {
        val list: Array<Array<Any>> = arrayOf(
            arrayOf(0, 1, 0),
            arrayOf(1, 1, 1),
            arrayOf(0, 1, 0),
        )

        assertEquals(
            actual = ConnectType.Cross,
            expected = useCase(list),
        )
    }

    @Test
    fun type15_2() {
        val list: Array<Array<Any>> = arrayOf(
            arrayOf(0, 2, 0),
            arrayOf(2, 2, 2),
            arrayOf(0, 2, 0),
        )

        assertEquals(
            actual = ConnectType.Cross,
            expected = useCase(list),
        )
    }
}
