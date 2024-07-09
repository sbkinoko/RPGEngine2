package map.manager

import map.MapModule
import map.data.LoopTestMap
import map.domain.BackgroundCell
import map.domain.Velocity
import map.domain.collision.Square
import map.usecase.MoveBackgroundUseCase
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BackgroundManagerTestLoopMap {

    private lateinit var backgroundManager: BackgroundManager
    private val moveBackgroundUseCase = MoveBackgroundUseCase()

    private val mapData = LoopTestMap()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                MapModule,
            )
        }

        backgroundManager = BackgroundManager(
            cellNum = CELL_NUM,
            sideLength = SIDE_LENGTH,
            mapData = mapData,
        )
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun checkFirstPosition() {
        backgroundManager.getCell(0, 0).apply {
            square.apply {
                assertEquals(
                    expected = 0f,
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
     *  背景を上に移動させた時のテスト
     */
    @Test
    fun checkLoop_Up() {
        val dy = 15f
        moveBackgroundUseCase.invoke(
            velocity = Velocity(
                x = 0f,
                y = -dy,
                maxVelocity = dy,
            ),
            fieldSquare = Square(
                x = 0f,
                y = 0f,
                size = SIDE_LENGTH.toFloat(),
            ),
            diffOfLoop = backgroundManager.diffOfLoop,
            allCellNum = backgroundManager.allCellNum,
            mapData = mapData,
        )

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
        moveBackgroundUseCase(
            velocity = Velocity(
                x = 0f,
                y = dy,
                maxVelocity = dy,
            ),
            fieldSquare = Square(
                x = 0f,
                y = 0f,
                size = SIDE_LENGTH.toFloat(),
            ),
            diffOfLoop = backgroundManager.diffOfLoop,
            allCellNum = backgroundManager.allCellNum,
            mapData = mapData,
        )

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
        val dx = 15f
        moveBackgroundUseCase(
            velocity = Velocity(
                x = -dx,
                y = 0f,
                maxVelocity = dx,
            ),
            fieldSquare = Square(
                x = 0f,
                y = 0f,
                size = SIDE_LENGTH.toFloat(),
            ),
            diffOfLoop = backgroundManager.diffOfLoop,
            allCellNum = backgroundManager.allCellNum,
            mapData = mapData,
        )

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
        moveBackgroundUseCase(
            velocity = Velocity(
                x = dx,
                y = 0f,
                maxVelocity = dx,
            ),
            fieldSquare = Square(
                x = 0f,
                y = 0f,
                size = SIDE_LENGTH.toFloat(),
            ),
            diffOfLoop = backgroundManager.diffOfLoop,
            allCellNum = backgroundManager.allCellNum,
            mapData = mapData,
        )

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
