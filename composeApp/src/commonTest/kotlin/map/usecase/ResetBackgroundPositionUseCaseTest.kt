package map.usecase

import map.MapModule
import map.data.LoopTestMap
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

class ResetBackgroundPositionUseCaseTest : KoinTest {
    private val mapData = LoopTestMap()

    private val resetBackgroundPositionUseCase: ResetBackgroundPositionUseCase
            by inject()
    private lateinit var backgroundManager: BackgroundManager
    private val repository: BackgroundRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                MapModule
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
    fun resetPosition() {
        repository.getBackgroundAt(
            x = 0,
            y = 0,
        ).apply {
            square.apply {
                assertEquals(
                    0f,
                    x,
                )
                assertEquals(
                    0f,
                    y,
                )
            }
            mapPoint.apply {
                assertEquals(
                    3,
                    x,
                )
                assertEquals(
                    3,
                    y,
                )
            }
        }

        resetBackgroundPositionUseCase(
            mapX = 1,
            mapY = 1,
            allCellNum = backgroundManager.allCellNum,
            cellNum = backgroundManager.cellNum,
            cellSize = backgroundManager.cellSize,
            mapData = mapData,
        )

        repository.getBackgroundAt(
            x = 0,
            y = 0,
        ).apply {
            square.apply {
                assertEquals(
                    0f,
                    x,
                )
                assertEquals(
                    0f,
                    y,
                )
            }
            mapPoint.apply {
                assertEquals(
                    0,
                    x,
                )
                assertEquals(
                    0,
                    y,
                )
            }
        }
    }
}