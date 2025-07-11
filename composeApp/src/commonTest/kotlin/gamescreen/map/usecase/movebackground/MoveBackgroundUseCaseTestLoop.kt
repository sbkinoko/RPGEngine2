package gamescreen.map.usecase.movebackground

import gamescreen.map.ModuleMap
import gamescreen.map.data.LoopTestMap
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.collision.square.NormalRectangle
import gamescreen.map.manager.CELL_NUM
import gamescreen.map.manager.SIDE_LENGTH
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.usecase.resetposition.ResetBackgroundPositionUseCase
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MoveBackgroundUseCaseTestLoop : KoinTest {
    private val moveBackgroundUseCase: MoveBackgroundUseCase by inject()
    private val repository: BackgroundRepository by inject()
    private val resetBackgroundPositionUseCase: ResetBackgroundPositionUseCase by inject()

    private val mapData = LoopTestMap()

    private lateinit var backgroundData: BackgroundData

    private val BackgroundData.leftTopCell
        get() = this.fieldData[0][0]

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleMap,
            )
        }

        repository.cellNum = CELL_NUM
        repository.screenSize = SIDE_LENGTH

        backgroundData = resetBackgroundPositionUseCase(
            mapData = mapData,
            mapX = 0,
            mapY = 0,
        )
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun checkFirstPosition() {
        backgroundData.leftTopCell.apply {
            rectangle.apply {
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
        runBlocking {
            moveBackgroundUseCase.invoke(
                velocity = Velocity(
                    x = 0f,
                    y = -dy,
                    maxVelocity = dy,
                ),
                fieldSquare = NormalRectangle(
                    x = 0f,
                    y = 0f,
                    size = SIDE_LENGTH.toFloat(),
                ),
                backgroundData = backgroundData,
            ).leftTopCell.apply {
                rectangle.apply {
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
    }

    /**
     *  背景を下に移動させた時のテスト
     */
    @Test
    fun checkLoop_Down() {
        val dy = 35f

        runBlocking {
            moveBackgroundUseCase.invoke(
                velocity = Velocity(
                    x = 0f,
                    y = dy,
                    maxVelocity = dy,
                ),
                fieldSquare = NormalRectangle(
                    x = 0f,
                    y = 0f,
                    size = SIDE_LENGTH.toFloat(),
                ),
                backgroundData = backgroundData
            ).leftTopCell.apply {
                rectangle.apply {
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
    }

    /**
     * 左に背景を移動させた時のテスト
     */
    @Test
    fun checkLoop_Left() {
        val dx = 15f

        runBlocking {
            moveBackgroundUseCase.invoke(
                velocity = Velocity(
                    x = -dx,
                    y = 0f,
                    maxVelocity = dx,
                ),
                fieldSquare = NormalRectangle(
                    x = 0f,
                    y = 0f,
                    size = SIDE_LENGTH.toFloat(),
                ),
                backgroundData = backgroundData,
            ).leftTopCell.apply {
                rectangle.apply {
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
    }

    /**
     * 右に背景を移動させた時のテスト
     */
    @Test
    fun checkLoop_Right() {
        val dx = 35f

        runBlocking {
            moveBackgroundUseCase.invoke(
                velocity = Velocity(
                    x = dx,
                    y = 0f,
                    maxVelocity = dx,
                ),
                fieldSquare = NormalRectangle(
                    x = 0f,
                    y = 0f,
                    size = SIDE_LENGTH.toFloat(),
                ),
                backgroundData = backgroundData,
            ).leftTopCell.apply {
                rectangle.apply {
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
    }

    companion object {
        private const val LEFT_TOP_CELL_MAP_X = 3
        private const val LEFT_TOP_CELL_MAP_Y = 3
    }
}
