package manager.map

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BackgroundManagerTest {
    companion object {
        const val CELL_NUM = 3
        const val SIDE_LENGTH = 30
    }


    private lateinit var backgroundManager: BackgroundManager

    @BeforeTest
    fun beforeTest() {
        backgroundManager = BackgroundManager(
            cellNum = CELL_NUM,
            sideLength = SIDE_LENGTH,
        )
    }

    @Test
    fun countCell() {
        backgroundManager.getCell(
            col = 3,
            row = 3,
        )
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

    /**
     *  背景を上に移動させた時のテスト
     */
    @Test
    fun checkLoop_Up(){
        backgroundManager.moveBackgroundCell(dy = -15f)
        backgroundManager.getCell(0, 0).apply {
            square.apply {
                assertEquals(
                    expected = 0f,
                    actual = leftSide,
                )
                assertEquals(
                    expected = 25f,
                    actual = topSide,
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
        backgroundManager.moveBackgroundCell(dy = dy)
        backgroundManager.getCell(0, 0).apply {
            square.apply {
                assertEquals(
                    expected = 0f,
                    actual = leftSide,
                )
                assertEquals(
                    expected = -dy + SIDE_LENGTH,
                    actual = topSide,
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
        backgroundManager.moveBackgroundCell(dx = -15f)
        backgroundManager.getCell(0, 0).apply {
            square.apply {
                assertEquals(
                    expected = 25f,
                    actual = leftSide,
                )
                assertEquals(
                    expected = 0f,
                    actual = topSide,
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
        backgroundManager.moveBackgroundCell(dx = dx)
        backgroundManager.getCell(0, 0).apply {
            square.apply {
                assertEquals(
                    expected = -dx + SIDE_LENGTH,
                    actual = leftSide,
                )
                assertEquals(
                    expected = 0f,
                    actual = topSide,
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
