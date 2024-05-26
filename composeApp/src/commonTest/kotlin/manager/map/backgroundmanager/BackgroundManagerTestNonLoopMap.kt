package manager.map.backgroundmanager

import data.map.mapdata.NonLoopTestMap
import manager.map.BackgroundManager
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BackgroundManagerTestNonLoopMap {

    private lateinit var backgroundManager: BackgroundManager

    @BeforeTest
    fun beforeTest() {
        backgroundManager = BackgroundManager(
            cellNum = CELL_NUM,
            sideLength = SIDE_LENGTH,
        )
        backgroundManager.mapData = NonLoopTestMap()
    }

    /**
     *  背景を上に移動させた時のテスト
     */
    @Test
    fun checkLoop_Up() {
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
    fun checkLoop_Left() {
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
