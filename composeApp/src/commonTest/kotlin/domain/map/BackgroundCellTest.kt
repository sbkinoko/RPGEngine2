package domain.map

import kotlin.test.Test
import kotlin.test.assertEquals

class BackgroundCellTest {

    @Test
    fun checkInit() {
        val x = 0f
        val y = 0f
        val size = 10f

        val bgCell = BackgroundCell(
            x = x,
            y = y,
            cellSize = size
        )

        bgCell.apply {
            assertEquals(
                expected = x,
                actual = square.leftSide,
            )
            assertEquals(
                expected = y,
                actual = square.topSide,
            )
            assertEquals(
                expected = size,
                actual = square.size
            )
        }
    }
}
