package map.usecase

import map.MapModule
import map.data.LoopTestMap
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
    private val repository: BackgroundRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                MapModule
            )
        }

        repository.cellNum = CELL_NUM
        repository.screenSize = SIDE_LENGTH
        resetBackgroundPositionUseCase(
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
            mapData = mapData,
            mapX = 1,
            mapY = 1,
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
