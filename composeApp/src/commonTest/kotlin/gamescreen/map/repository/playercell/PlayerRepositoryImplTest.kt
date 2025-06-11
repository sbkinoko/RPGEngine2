package gamescreen.map.repository.playercell

import gamescreen.map.ModuleMap
import gamescreen.map.domain.MapPoint
import gamescreen.map.domain.background.BackgroundCell
import gamescreen.map.domain.collision.square.NormalRectangle
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

    private val backgroundCell = BackgroundCell(
        rectangle = NormalRectangle(
            x = 10f,
            y = 10f,
            size = 10f,
        ),
        mapPoint = MapPoint(),
        collisionData = emptyList(),
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
    fun initial() {
        playerCellRepository.playerIncludeCell = null
    }

    @Test
    fun checkFlow() {
        runBlocking {
            var count = 0

            val collectJob = launch {
                playerCellRepository.playerIncludeCellFlow.collect {
                    count++
                    assertEquals(
                        expected = backgroundCell,
                        actual = it,
                    )
                }
            }

            playerCellRepository.playerIncludeCell = backgroundCell

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

        playerCellRepository.playerIncludeCell = backgroundCell

        assertEquals(
            expected = backgroundCell,
            actual = playerCellRepository.playerIncludeCell,
        )

        assertEquals(
            expected = backgroundCell,
            actual = playerCellRepository.eventCell,
        )
    }

    /**
     * 同一のマスをセットした場合のテスト
     * 移動なし
     */
    @Test
    fun setTwice() {
        playerCellRepository.playerIncludeCell = backgroundCell
        playerCellRepository.playerIncludeCell = backgroundCell

        assertEquals(
            expected = null,
            actual = playerCellRepository.eventCell,
        )

        assertEquals(
            expected = backgroundCell,
            actual = playerCellRepository.playerIncludeCell,
        )
    }

    /**
     * 同一のマスをセットした場合のテスト
     * 移動あり
     */
    @Test
    fun setSameCell() {
        playerCellRepository.playerIncludeCell = backgroundCell

        val backGroundCell2 = backgroundCell.copy(
            rectangle = NormalRectangle(
                x = 11f,
                y = 11f,
                size = 10f,
            )
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
        playerCellRepository.playerIncludeCell = backgroundCell

        val backGroundCell2 = backgroundCell.copy(
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
