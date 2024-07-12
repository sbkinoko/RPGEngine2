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

class GetScreenCenterUseCaseTest : KoinTest {
    private val resetBackgroundPositionUseCase: ResetBackgroundPositionUseCase by inject()
    private val useCase: GetScreenCenterUseCase by inject()
    private val repository: BackgroundRepository by inject()
    private val mapData = LoopTestMap()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                MapModule,
            )
        }
        repository.cellNum = CELL_NUM
        repository.screenSize = SIDE_LENGTH
        resetBackgroundPositionUseCase(
            mapData = mapData,
            mapX = 1,
            mapY = 1,
        )
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun getScreenCenter() {
        useCase().apply {
            assertEquals(
                expected = SIDE_LENGTH / 2f,
                actual = x,
            )
            assertEquals(
                expected = SIDE_LENGTH / 2f,
                actual = y,
            )
        }
    }
}
