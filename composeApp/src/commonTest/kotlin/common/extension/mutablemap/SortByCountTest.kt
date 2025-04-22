package common.extension.mutablemap

import common.extension.MapCounter
import common.extension.sortByCount
import core.domain.mapcell.CellType
import kotlin.test.Test
import kotlin.test.assertEquals

class SortByCountTest {

    @Test
    fun test1() {
        val mapCounter = MapCounter<CellType>()

        mapCounter.inc(CellType.Sand)
        mapCounter.inc(CellType.Sand)
        mapCounter.inc(CellType.Glass)

        val ans = mapCounter.ranking

        assertEquals(
            expected = CellType.Sand,
            actual = ans[0]
        )

        assertEquals(
            expected = CellType.Glass,
            actual = ans[1]
        )
    }

    @Test
    fun test2() {
        val map = mutableMapOf(
            CellType.Glass to 3,
            CellType.Sand to 1,
            CellType.Water to 2,
        )
        val ans = map.sortByCount()

        assertEquals(
            expected = CellType.Glass,
            actual = ans[0]
        )

        assertEquals(
            expected = CellType.Water,
            actual = ans[1]
        )

        assertEquals(
            expected = CellType.Sand,
            actual = ans[2]
        )
    }
}
