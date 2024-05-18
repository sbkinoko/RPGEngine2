package manager.map

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BackgroundCellManagerTest {
    companion object {
        const val CELL_NUM = 3
        const val SIDE_LENGTH = 30
    }


    private lateinit var backgroundCellManager: BackgroundCellManager

    @BeforeTest
    fun beforeTest() {
        backgroundCellManager = BackgroundCellManager(
            cellNum = CELL_NUM,
            sideLength = SIDE_LENGTH,
        )
    }

    @Test
    fun countCell() {
        backgroundCellManager.getCell(
            col = 3,
            row = 3,
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

    /**
     *  背景を上に移動させた時のテスト
     */
    @Test
    fun checkLoop_Up(){
        backgroundCellManager.moveBackgroundCell(dy = -15f)
        backgroundCellManager.getCell(0, 0).apply {
            displayPoint.apply {
                assertEquals(
                    expected = 0f,
                    actual = x,
                )
                assertEquals(
                    expected = 25f,
                    actual = y,
                )
            }
            mapPoint.apply {
                assertEquals(
                    expected = 0,
                    actual = x,
                )
                assertEquals(
                    expected = 4,
                    actual = y,
                )
            }
        }

    }

    /**
     *  背景を下に移動させた時のテスト
     */
    @Test
    fun checkLoop_Down() {
        val dy = 35f
        backgroundCellManager.moveBackgroundCell(dy = dy)
        backgroundCellManager.getCell(0, 0).apply {
            displayPoint.apply {
                assertEquals(
                    expected = 0f,
                    actual = x,
                )
                assertEquals(
                    expected = -dy + SIDE_LENGTH,
                    actual = y,
                )
            }
            mapPoint.apply {
                assertEquals(
                    expected = 0,
                    actual = x,
                )
                assertEquals(
                    expected = -4,
                    actual = y,
                )
            }
        }
    }

    /**
     * 左に背景を移動させた時のテスト
     */
    @Test
    fun checkLoop_LeftUp() {
        backgroundCellManager.moveBackgroundCell(dx = -15f)
        backgroundCellManager.getCell(0, 0).apply {
            displayPoint.apply {
                assertEquals(
                    expected = 25f,
                    actual = x,
                )
                assertEquals(
                    expected = 0f,
                    actual = y,
                )
            }
            mapPoint.apply {
                assertEquals(
                    expected = 4,
                    actual = x,
                )
                assertEquals(
                    expected = 0,
                    actual = y,
                )
            }
        }

    }

    /**
     * 右に背景を移動させた時のテスト
     */
    @Test
    fun checkLoop_Right() {
        val dx = 35f
        backgroundCellManager.moveBackgroundCell(dx = dx)
        backgroundCellManager.getCell(0, 0).apply {
            displayPoint.apply {
                assertEquals(
                    expected = -dx + SIDE_LENGTH,
                    actual = x,
                )
                assertEquals(
                    expected = 0f,
                    actual = y,
                )
            }
            mapPoint.apply {
                assertEquals(
                    expected = -4,
                    actual = x,
                )
                assertEquals(
                    expected = 0,
                    actual = y,
                )
            }
        }
    }
}
