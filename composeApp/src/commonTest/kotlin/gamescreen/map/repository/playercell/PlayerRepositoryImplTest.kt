package gamescreen.map.repository.playercell

import gamescreen.map.ModuleMap
import gamescreen.map.domain.BackgroundCell
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
    fun setOnce() {
        val backGroundCell = BackgroundCell(
            cellSize = 10f,
            x = 10f,
            y = 10f,
        )
        playerCellRepository.playerIncludeCell = backGroundCell

        assertEquals(
            expected = backGroundCell,
            actual = playerCellRepository.playerIncludeCell
        )
    }

    @Test
    fun setTwice() {
        val backGroundCell = BackgroundCell(
            cellSize = 10f,
            x = 10f,
            y = 10f,
        )
        playerCellRepository.playerIncludeCell = backGroundCell
        playerCellRepository.playerIncludeCell = backGroundCell

        assertEquals(
            expected = null,
            actual = playerCellRepository.playerIncludeCell
        )
    }

    @Test
    fun setTwoKind() {

        val backGroundCell = BackgroundCell(
            cellSize = 10f,
            x = 10f,
            y = 10f,
        )
        playerCellRepository.playerIncludeCell = backGroundCell

        val backGroundCell2 = BackgroundCell(
            cellSize = 10f,
            x = 10f,
            y = 10f,
        )
        playerCellRepository.playerIncludeCell = backGroundCell2

        assertEquals(
            expected = backGroundCell2,
            actual = playerCellRepository.playerIncludeCell
        )
    }
}
