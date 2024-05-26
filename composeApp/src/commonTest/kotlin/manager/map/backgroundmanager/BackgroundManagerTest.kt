package manager.map.backgroundmanager

import data.map.mapdata.LoopTestMap
import manager.map.BackgroundManager
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BackgroundManagerTest {

    private lateinit var backgroundManager: BackgroundManager

    @BeforeTest
    fun beforeTest() {
        backgroundManager = BackgroundManager(
            cellNum = CELL_NUM,
            sideLength = SIDE_LENGTH,
        )
        backgroundManager.mapData = LoopTestMap()
    }

    @Test
    fun countCell() {
        backgroundManager.getCell(
            col = 0,
            row = 0,
        ).apply {
            assertEquals(
                expected = 0,
                this.imgID,
            )
        }
        backgroundManager.getCell(
            col = 2,
            row = 2,
        ).apply {
            assertEquals(
                expected = 10,
                this.imgID,
            )
        }
    }

    @Test
    fun checkInitPosition() {
        val cell1 = backgroundManager.getCell(0, 0)

        assertEquals(
            expected = 10f,
            actual = cell1.cellSize,
        )

        cell1.square.apply {
            assertEquals(
                expected = 0f,
                actual = leftSide,
            )
            assertEquals(
                expected = 0f,
                actual = topSide,
            )
        }

        val cell2 = backgroundManager.getCell(1, 0)
        cell2.square.apply {
            assertEquals(
                expected = 10f,
                actual = leftSide,
            )
            assertEquals(
                expected = 0f,
                actual = topSide,
            )
        }

        val cell3 = backgroundManager.getCell(0, 1)
        cell3.square.apply {
            assertEquals(
                expected = 0f,
                actual = leftSide,
            )
            assertEquals(
                expected = 10f,
                actual = topSide,
            )
        }
    }

    @Test
    fun move() {
        backgroundManager.moveBackgroundCell(dx = 10f, dy = 5f)
        backgroundManager.getCell(0, 0).square.apply {
            assertEquals(
                expected = 10f,
                actual = leftSide,
            )
            assertEquals(
                expected = 5f,
                actual = topSide,
            )
        }
    }
}
