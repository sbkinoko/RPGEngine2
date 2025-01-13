package gamescreen.map.domain

import gamescreen.map.domain.collision.square.NormalSquare
import kotlin.test.Test
import kotlin.test.assertEquals

class BackgroundCellTest {
    private val x = 0f
    private val y = 0f
    private val size = 10f

    private val square = NormalSquare(
        x = x,
        y = y,
        size = size,
    )

    private val mapPoint = MapPoint()

    private val bgCell = BackgroundCell(
        square = square,
        mapPoint = mapPoint,
    )

    @Test
    fun checkSquare() {
        assertEquals(
            expected = square,
            actual = bgCell.square,
        )
    }

    @Test
    fun checkMapPoint() {
        assertEquals(
            expected = mapPoint,
            actual = bgCell.mapPoint
        )
    }
}
