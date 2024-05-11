package domain.map

import kotlin.test.Test
import kotlin.test.assertEquals

class BackgroundCellTest {

    @Test
    fun move() {
        val bgCell = BackgroundCell()
        val dx = 1f
        val dy = 1f
        bgCell.moveDisplayPoint(
            dx = dx,
            dy = dy,
        )

        assertEquals(
            expected = 1f,
            actual = bgCell.displayPoint.x,
        )

        assertEquals(
            expected = 1f,
            actual = bgCell.displayPoint.y,
        )
    }
}
