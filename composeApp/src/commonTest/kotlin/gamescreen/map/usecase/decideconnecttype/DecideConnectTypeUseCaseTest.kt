package gamescreen.map.usecase.decideconnecttype

import core.domain.mapcell.CellType
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
        val list: Array<Array<CellType>> = arrayOf(
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
            arrayOf(CellType.Null, CellType.Null, CellType.Null),
        )

        assertEquals(
            actual = ConnectType.Vertical,
            expected = useCase(list),
        )
    }

    @Test
    fun type2() {
        val list: Array<Array<CellType>> = arrayOf(
            arrayOf(CellType.Null, CellType.Null, CellType.Null),
            arrayOf(CellType.Glass, CellType.Glass, CellType.Null),
            arrayOf(CellType.Null, CellType.Null, CellType.Null),
        )

        assertEquals(
            actual = ConnectType.Horizontal,
            expected = useCase(list),
        )
    }

    @Test
    fun type3() {
        val list: Array<Array<CellType>> = arrayOf(
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
            arrayOf(CellType.Glass, CellType.Glass, CellType.Null),
            arrayOf(CellType.Null, CellType.Null, CellType.Null),
        )

        assertEquals(
            actual = ConnectType.LeftTopUp,
            expected = useCase(list),
        )
    }

    @Test
    fun type4() {
        val list: Array<Array<CellType>> = arrayOf(
            arrayOf(CellType.Null, CellType.Null, CellType.Null),
            arrayOf(CellType.Null, CellType.Glass, CellType.Glass),
            arrayOf(CellType.Null, CellType.Null, CellType.Null),
        )

        assertEquals(
            actual = ConnectType.Horizontal,
            expected = useCase(list),
        )
    }

    @Test
    fun type5() {
        val list: Array<Array<CellType>> = arrayOf(
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
            arrayOf(CellType.Null, CellType.Glass, CellType.Glass),
            arrayOf(CellType.Null, CellType.Null, CellType.Null),
        )

        assertEquals(
            actual = ConnectType.RightToUp,
            expected = useCase(list),
        )
    }

    @Test
    fun type6() {
        val list: Array<Array<CellType>> = arrayOf(
            arrayOf(CellType.Null, CellType.Null, CellType.Null),
            arrayOf(CellType.Glass, CellType.Glass, CellType.Glass),
            arrayOf(CellType.Null, CellType.Null, CellType.Null),
        )

        assertEquals(
            actual = ConnectType.Horizontal,
            expected = useCase(list),
        )
    }

    @Test
    fun type7() {
        val list: Array<Array<CellType>> = arrayOf(
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
            arrayOf(CellType.Glass, CellType.Glass, CellType.Glass),
            arrayOf(CellType.Null, CellType.Null, CellType.Null),
        )

        assertEquals(
            actual = ConnectType.WithoutDown,
            expected = useCase(list),
        )
    }

    @Test
    fun type8() {
        val list: Array<Array<CellType>> = arrayOf(
            arrayOf(CellType.Null, CellType.Null, CellType.Null),
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
        )

        assertEquals(
            actual = ConnectType.Vertical,
            expected = useCase(list),
        )
    }

    @Test
    fun type9() {
        val list: Array<Array<CellType>> = arrayOf(
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
        )

        assertEquals(
            actual = ConnectType.Vertical,
            expected = useCase(list),
        )
    }

    @Test
    fun type10() {
        val list: Array<Array<CellType>> = arrayOf(
            arrayOf(CellType.Null, CellType.Null, CellType.Null),
            arrayOf(CellType.Glass, CellType.Glass, CellType.Null),
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
        )

        assertEquals(
            actual = ConnectType.LeftTopDown,
            expected = useCase(list),
        )
    }

    @Test
    fun type11() {
        val list: Array<Array<CellType>> = arrayOf(
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
            arrayOf(CellType.Glass, CellType.Glass, CellType.Null),
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
        )

        assertEquals(
            actual = ConnectType.WithoutRight,
            expected = useCase(list),
        )
    }

    @Test
    fun type12() {
        val list: Array<Array<CellType>> = arrayOf(
            arrayOf(CellType.Null, CellType.Null, CellType.Null),
            arrayOf(CellType.Null, CellType.Glass, CellType.Glass),
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
        )

        assertEquals(
            actual = ConnectType.RightToDown,
            expected = useCase(list),
        )
    }

    @Test
    fun type13() {
        val list: Array<Array<CellType>> = arrayOf(
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
            arrayOf(CellType.Null, CellType.Glass, CellType.Glass),
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
        )

        assertEquals(
            actual = ConnectType.WithoutLeft,
            expected = useCase(list),
        )
    }

    @Test
    fun type14() {
        val list: Array<Array<CellType>> = arrayOf(
            arrayOf(CellType.Null, CellType.Null, CellType.Null),
            arrayOf(CellType.Glass, CellType.Glass, CellType.Glass),
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
        )

        assertEquals(
            actual = ConnectType.WithoutUp,
            expected = useCase(list),
        )
    }

    @Test
    fun type15() {
        val list: Array<Array<CellType>> = arrayOf(
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
            arrayOf(CellType.Glass, CellType.Glass, CellType.Glass),
            arrayOf(CellType.Null, CellType.Glass, CellType.Null),
        )

        assertEquals(
            actual = ConnectType.Cross,
            expected = useCase(list),
        )
    }

    @Test
    fun type15_2() {
        val list: Array<Array<CellType>> = arrayOf(
            arrayOf(CellType.Null, CellType.Water, CellType.Null),
            arrayOf(CellType.Water, CellType.Water, CellType.Water),
            arrayOf(CellType.Null, CellType.Water, CellType.Null),
        )

        assertEquals(
            actual = ConnectType.Cross,
            expected = useCase(list),
        )
    }
}
