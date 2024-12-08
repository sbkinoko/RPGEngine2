package gamescreen.map.usecase

import gamescreen.map.ModuleMap
import gamescreen.map.data.LoopTestMap
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.Square
import gamescreen.map.manager.CELL_NUM
import gamescreen.map.manager.SIDE_LENGTH
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MoveBackgroundUseCaseTest : KoinTest {
    private val moveBackgroundUseCase: MoveBackgroundUseCase by inject()
    private val repository: BackgroundRepository by inject()
    private val resetBackgroundPositionUseCase: ResetBackgroundPositionUseCase by inject()

    private val mapData = LoopTestMap()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleMap,
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
    fun checkInitPosition() {
        val cell1 = repository.getBackgroundAt(
            x = 0,
            y = 0,
        )

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

        val cell2 = repository.getBackgroundAt(
            x = 1,
            y = 0,
        )
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

        val cell3 = repository.getBackgroundAt(
            x = 0,
            y = 1,
        )
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
        val dx = 10f
        val dy = 5f
        val vMax = 20f
        moveBackgroundUseCase(
            velocity = Velocity(
                x = dx,
                y = dy,
                maxVelocity = vMax,
            ),
            fieldSquare = Square(
                x = 0f,
                y = 0f,
                size = SIDE_LENGTH.toFloat(),
            ),
        )

        repository.getBackgroundAt(
            x = 0,
            y = 0,
        ).square.apply {
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
}
