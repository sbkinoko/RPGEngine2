package map.usecase

import map.MapModule
import map.data.NonLoopTestMap
import map.domain.Velocity
import map.domain.collision.Square
import map.manager.BackgroundManager
import map.manager.CELL_NUM
import map.manager.SIDE_LENGTH
import map.repository.backgroundcell.BackgroundRepository
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MoveBackGroundUseCateTestNonLoop : KoinTest {
    private lateinit var backgroundManager: BackgroundManager
    private val moveBackgroundUseCase: MoveBackgroundUseCase by inject()
    private val repository: BackgroundRepository by inject()
    private val resetBackgroundPositionUseCase: ResetBackgroundPositionUseCase by inject()

    private val mapData = NonLoopTestMap()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                MapModule,
            )
        }

        repository.mapData = mapData

        backgroundManager = BackgroundManager(
            cellNum = CELL_NUM,
            sideLength = SIDE_LENGTH,
        )

        resetBackgroundPositionUseCase(
            allCellNum = backgroundManager.allCellNum,
            cellNum = backgroundManager.cellNum,
            cellSize = backgroundManager.cellSize,
            mapX = 0,
            mapY = 0,
        )
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun heckFirstPosition() {
        repository.getBackgroundAt(
            x = 0,
            y = 0,
        ).apply {
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
                    expected = INITIAL_LEFT_TOP_MAP_X,
                    actual = x,
                )
                assertEquals(
                    expected = INITIAL_LEFT_TOP_MAP_Y,
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
        )

        repository.getBackgroundAt(
            x = 0,
            y = 0,
        ).apply {
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
                    expected = INITIAL_LEFT_TOP_MAP_X,
                    actual = x,
                )
                assertEquals(
                    expected = 3,
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
        )

        repository.getBackgroundAt(
            x = 0,
            y = 0,
        ).apply {
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
                    expected = INITIAL_LEFT_TOP_MAP_X,
                    actual = x,
                )
                assertEquals(
                    expected = -5,
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
        )

        repository.getBackgroundAt(
            x = 0,
            y = 0,
        ).apply {
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
                    expected = 3,
                    actual = x,
                )
                assertEquals(
                    expected = INITIAL_LEFT_TOP_MAP_Y,
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
        )

        repository.getBackgroundAt(
            x = 0,
            y = 0,
        ).apply {
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
                    expected = -5,
                    actual = x,
                )
                assertEquals(
                    expected = INITIAL_LEFT_TOP_MAP_Y,
                    actual = y,
                )
            }
        }
    }

    companion object {
        const val INITIAL_LEFT_TOP_MAP_X = -1
        const val INITIAL_LEFT_TOP_MAP_Y = -1
    }
}
