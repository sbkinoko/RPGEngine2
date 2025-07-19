package gamescreen.map.domain

import core.domain.mapcell.CellType
import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.collision.square.NormalRectangle
import kotlin.test.Test
import kotlin.test.assertEquals

class BackgroundCellTest {
    private val x = 0f
    private val y = 0f
    private val size = 10f

    private val square = NormalRectangle(
        x = x,
        y = y,
        size = size,
    )

    private val mapPoint = MapPoint()

    private val bgCell = BackgroundCell(
        rectangle = square,
        mapPoint = mapPoint,
        collisionData = emptyList(),
        aroundCellId = emptyList(),
        cellType = CellType.Null,
    )

    @Test
    fun checkSquare() {
        assertEquals(
            expected = square,
            actual = bgCell.rectangle,
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
