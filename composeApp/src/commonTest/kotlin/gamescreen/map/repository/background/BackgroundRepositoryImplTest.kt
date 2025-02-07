package gamescreen.map.repository.background

import core.domain.mapcell.CellType
import gamescreen.map.ModuleMap
import gamescreen.map.data.LoopTestMap
import gamescreen.map.data.NonLoopTestMap
import gamescreen.map.domain.MapPoint
import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BackgroundRepositoryImplTest : KoinTest {

    private val repository: BackgroundRepository by inject()

    private val background = BackgroundData(
        List(3)
        { row ->
            List(3) { col ->
                BackgroundCell(
                    square = NormalSquare(
                        x = row * 10f,
                        y = col * 10f,
                        size = 10f,
                    ),
                    mapPoint = MapPoint(),
                )
            }
        }
    )

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleMap,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun setTest() {
        runBlocking {
            repository.setBackground(
                background = background
            )

            repository.backgroundStateFlow.value.apply {
                assertEquals(
                    expected = background,
                    actual = this,
                )
            }

            repository.getBackgroundAt(0, 0).apply {
                assertEquals(
                    expected = background.fieldData[0][0],
                    actual = this,
                )
            }
        }
    }

    @Test
    fun checkFlow() {
        runBlocking {
            lateinit var result: BackgroundData
            var count = 0
            val collectJob: Job = launch {
                repository.backgroundStateFlow.collect {
                    count++
                    result = it
                }
            }

            repository.setBackground(
                background = background
            )

            delay(100)

            assertEquals(
                expected = background,
                actual = result,
            )
            assertEquals(
                expected = 1,
                actual = count
            )

            collectJob.cancel()
        }
    }

    @Test
    fun getAroundCellNoOver() {
        repository.mapData = NonLoopTestMap()

        repository.getBackgroundAround(
            x = 1,
            y = 1,
        ).apply {
            assertEquals(
                expected = 0,
                actual = (this[0][0] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 1,
                actual = (this[0][1] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 2,
                actual = (this[0][2] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 4,
                actual = (this[1][0] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 5,
                actual = (this[1][1] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 6,
                actual = (this[1][2] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 8,
                actual = (this[2][0] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 9,
                actual = (this[2][1] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 10,
                actual = (this[2][2] as CellType.TextCell).id,
            )
        }
    }

    @Test
    fun getAroundCellOverNoLoop() {
        repository.mapData = NonLoopTestMap()

        repository.getBackgroundAround(
            x = 0,
            y = 0,
        ).apply {
            assertEquals(
                expected = CellType.Null,
                actual = this[0][0],
            )
            assertEquals(
                expected = CellType.Null,
                actual = this[0][1],
            )
            assertEquals(
                expected = CellType.Null,
                actual = this[0][2],
            )

            assertEquals(
                expected = CellType.Null,
                actual = this[1][0],
            )
            assertEquals(
                expected = 0,
                actual = (this[1][1] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 1,
                actual = (this[1][2] as CellType.TextCell).id,
            )

            assertEquals(
                expected = CellType.Null,
                actual = this[2][0],
            )

            assertEquals(
                expected = 4,
                actual = (this[2][1] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 5,
                actual = (this[2][2] as CellType.TextCell).id,
            )
        }
    }

    @Test
    fun getAroundCellOverLoop() {
        repository.mapData = LoopTestMap()

        repository.getBackgroundAround(
            x = 0,
            y = 0,
        ).apply {
            assertEquals(
                expected = 15,
                actual = (this[0][0] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 12,
                actual = (this[0][1] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 13,
                actual = (this[0][2] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 3,
                actual = (this[1][0] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 0,
                actual = (this[1][1] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 1,
                actual = (this[1][2] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 7,
                actual = (this[2][0] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 4,
                actual = (this[2][1] as CellType.TextCell).id,
            )
            assertEquals(
                expected = 5,
                actual = (this[2][2] as CellType.TextCell).id,
            )
        }
    }
}
