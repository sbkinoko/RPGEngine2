package domain.map

import kotlin.test.Test
import kotlin.test.assertEquals

class BackgroundCellTest {

    // cellのdisplayPointの値が0の時のテスト
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

    // cellのdisplayPointの値が0以外の時のテスト
    @Test
    fun moveWithValue() {
        val bgCell = BackgroundCell()
        bgCell.displayPoint = Point(
            x = 2f,
            y = 2f,
        )
        val dx = 1f
        val dy = 1f
        bgCell.moveDisplayPoint(
            dx = dx,
            dy = dy,
        )

        assertEquals(
            expected = 3f,
            actual = bgCell.displayPoint.x,
        )

        assertEquals(
            expected = 3f,
            actual = bgCell.displayPoint.y,
        )
    }

    @Test
    fun side() {
        val backgroundCell = BackgroundCell()
        backgroundCell.displayPoint = Point(
            x = 10f,
            y = 15f,
        )
        backgroundCell.cellSize = 10f

        assertEquals(
            expected = 10f,
            actual = backgroundCell.leftSide
        )

        assertEquals(
            expected = 20f,
            actual = backgroundCell.rightSide,
        )

        assertEquals(
            expected = 15f,
            actual = backgroundCell.topSide,
        )

        assertEquals(
            expected = 25f,
            actual = backgroundCell.bottomSide,
        )
    }
}
