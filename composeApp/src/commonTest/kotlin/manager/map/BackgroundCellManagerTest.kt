package manager.map

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BackgroundCellManagerTest {
    lateinit var backgroundCellManager: BackgroundCellManager

    @BeforeTest
    fun beforeTest() {
        backgroundCellManager = BackgroundCellManager(
            cellNum = 3,
            sideLength = 30,
        )
    }

    @Test
    fun checkInitPosition() {
        val cell1 = backgroundCellManager.getCell(0, 0)

        assertEquals(
            expected = 10f,
            actual = cell1.cellSize,
        )

        cell1.displayPoint.apply {
            assertEquals(
                expected = 0f,
                actual = x,
            )
            assertEquals(
                expected = 0f,
                actual = y,
            )
        }

        val cell2 = backgroundCellManager.getCell(1, 0)
        cell2.displayPoint.apply {
            assertEquals(
                expected = 10f,
                actual = x,
            )
            assertEquals(
                expected = 0f,
                actual = y,
            )
        }

        val cell3 = backgroundCellManager.getCell(0, 1)
        cell3.displayPoint.apply {
            assertEquals(
                expected = 0f,
                actual = x,
            )
            assertEquals(
                expected = 10f,
                actual = y,
            )
        }
    }

    @Test
    fun move() {
        backgroundCellManager.moveBackgroundCell(dx = 10f, dy = 5f)
        backgroundCellManager.getCell(0, 0).displayPoint.apply {
            assertEquals(
                expected = 10f,
                actual = x,
            )
            assertEquals(
                expected = 5f,
                actual = y,
            )
        }
    }
}
