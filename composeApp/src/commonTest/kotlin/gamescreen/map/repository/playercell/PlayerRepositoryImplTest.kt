package gamescreen.map.repository.playercell

import gamescreen.map.ModuleMap
import gamescreen.map.domain.BackgroundCell
import gamescreen.map.domain.MapPoint
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

class PlayerRepositoryImplTest : KoinTest {
    private val playerCellRepository: PlayerCellRepository by inject()

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
    fun initial() {
        playerCellRepository.playerIncludeCell = null
    }

    @Test
    fun checkFlow() {
        runBlocking {
            val backGroundCell = BackgroundCell(
                cellSize = 10f,
                x = 10f,
                y = 10f,
                mapPoint = MapPoint(),
            )
            var count = 0

            val collectJob = launch {
                playerCellRepository.playerIncludeCellFlow.collect {
                    count++
                    assertEquals(
                        expected = backGroundCell,
                        actual = it,
                    )
                }
            }

            playerCellRepository.playerIncludeCell = backGroundCell

            delay(50)

            assertEquals(
                expected = 1,
                actual = count,
            )

            collectJob.cancel()
        }
    }

    /**
     * 一回セットしただけのテスト
     */
    @Test
    fun setOnce() {
        val backGroundCell = BackgroundCell(
            cellSize = 10f,
            x = 10f,
            y = 10f,
            mapPoint = MapPoint(),
        )
        playerCellRepository.playerIncludeCell = backGroundCell

        assertEquals(
            expected = backGroundCell,
            actual = playerCellRepository.playerIncludeCell,
        )

        assertEquals(
            expected = backGroundCell,
            actual = playerCellRepository.eventCell,
        )
    }

    /**
     * 同一のマスをセットした場合のテスト
     * 移動なし
     */
    @Test
    fun setTwice() {
        val backGroundCell = BackgroundCell(
            cellSize = 10f,
            x = 10f,
            y = 10f,
            mapPoint = MapPoint(),
        )
        playerCellRepository.playerIncludeCell = backGroundCell
        playerCellRepository.playerIncludeCell = backGroundCell

        assertEquals(
            expected = null,
            actual = playerCellRepository.eventCell,
        )

        assertEquals(
            expected = backGroundCell,
            actual = playerCellRepository.playerIncludeCell,
        )
    }

    /**
     * 同一のマスをセットした場合のテスト
     * 移動あり
     */
    @Test
    fun setSameCell() {
        val backGroundCell = BackgroundCell(
            cellSize = 10f,
            x = 10f,
            y = 10f,
            mapPoint = MapPoint(),
        )
        playerCellRepository.playerIncludeCell = backGroundCell

        val backGroundCell2 = backGroundCell.copy(
            x = 11f,
            y = 11f,
        )
        playerCellRepository.playerIncludeCell = backGroundCell2

        assertEquals(
            expected = null,
            actual = playerCellRepository.eventCell,
        )

        assertEquals(
            expected = backGroundCell2,
            actual = playerCellRepository.playerIncludeCell,
        )
    }

    /**
     * 違うマスをセットした場合のテスト
     */
    @Test
    fun setTwoKind() {
        val backGroundCell = BackgroundCell(
            cellSize = 10f,
            x = 10f,
            y = 10f,
            mapPoint = MapPoint(),
        )
        playerCellRepository.playerIncludeCell = backGroundCell

        val backGroundCell2 = BackgroundCell(
            cellSize = 10f,
            x = 10f,
            y = 10f,
            mapPoint = MapPoint(
                x = 1,
                y = 1,
            ),
        )
        playerCellRepository.playerIncludeCell = backGroundCell2

        assertEquals(
            expected = backGroundCell2,
            actual = playerCellRepository.playerIncludeCell
        )

        assertEquals(
            expected = backGroundCell2,
            actual = playerCellRepository.eventCell,
        )
    }
}
