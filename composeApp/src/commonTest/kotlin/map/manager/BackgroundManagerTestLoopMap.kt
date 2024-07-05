package map.manager

import map.data.LoopTestMap
import map.domain.BackgroundCell
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BackgroundManagerTestLoopMap {

    private lateinit var backgroundManager: BackgroundManager

    @BeforeTest
    fun beforeTest() {
        backgroundManager = BackgroundManager(
            cellNum = CELL_NUM,
            sideLength = SIDE_LENGTH,
            mapData = LoopTestMap()
        )
    }

    /**
     *  背景を上に移動させた時のテスト
     */
    @Test
    fun checkLoop_Up() {
        backgroundManager.moveBackgroundCell(dy = -15f)
        getLeftTopCell().apply {
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
                    expected = LEFT_TOP_CELL_MAP_X,
                    actual = x,
                )
                assertEquals(
                    expected = LEFT_TOP_CELL_MAP_Y,
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
        getLeftTopCell().apply {
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
                    expected = LEFT_TOP_CELL_MAP_X,
                    actual = x,
                )
                assertEquals(
                    expected = LEFT_TOP_CELL_MAP_Y,
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
        getLeftTopCell().apply {
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
                    expected = LEFT_TOP_CELL_MAP_X,
                    actual = x,
                )
                assertEquals(
                    expected = LEFT_TOP_CELL_MAP_Y,
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
        getLeftTopCell().apply {
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
                    expected = LEFT_TOP_CELL_MAP_X,
                    actual = x,
                )
                assertEquals(
                    expected = LEFT_TOP_CELL_MAP_Y,
                    actual = y,
                )
            }
        }
    }

    /**
     * 中心が(0,0)　左上のセルは(-1,-1) ループしてるので(3,3)
     */
    private fun getLeftTopCell(): BackgroundCell {
        return backgroundManager.getCell(0, 0)
    }

    companion object {
        private const val LEFT_TOP_CELL_MAP_X = 3
        private const val LEFT_TOP_CELL_MAP_Y = 3
    }
}
