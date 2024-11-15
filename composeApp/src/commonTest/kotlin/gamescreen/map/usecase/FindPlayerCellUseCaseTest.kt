package gamescreen.map.usecase

import gamescreen.map.MapModule
import gamescreen.map.data.LoopTestMap
import gamescreen.map.domain.collision.Square
import gamescreen.map.manager.CELL_NUM
import gamescreen.map.manager.SIDE_LENGTH
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.player.PlayerPositionRepository
import gamescreen.map.repository.playercell.PlayerCellRepository
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class FindPlayerCellUseCaseTest : KoinTest {
    private val resetBackgroundPositionUseCase: ResetBackgroundPositionUseCase by inject()
    private val findEventCellUseCase: FindEventCellUseCase by inject()

    private val backgroundRepository: BackgroundRepository by inject()
    private val playerCellRepository: PlayerCellRepository by inject()
    private val playerPositionRepository: PlayerPositionRepository by inject()
    private val mapData = LoopTestMap()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                MapModule
            )
        }

        backgroundRepository.cellNum = CELL_NUM
        backgroundRepository.screenSize = SIDE_LENGTH
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
    fun checkIncludeCell() {
        runBlocking {
            playerPositionRepository.setPlayerPosition(
                Square(
                    x = 1f,
                    y = 1f,
                    size = 5f,
                ),
            )

            // 最初に全身が入ってるからnullじゃない
            findEventCellUseCase()
            assertTrue {
                playerCellRepository.playerIncludeCell != null
            }

            // 前回のマスから動いてないからnull
            findEventCellUseCase()
            assertTrue {
                playerCellRepository.playerIncludeCell == null
            }

            // 全身が入ってないから動いたけどnull
            playerPositionRepository.setPlayerPosition(
                Square(
                    x = 6f,
                    y = 6f,
                    size = 5f,
                )
            )
            findEventCellUseCase()
            assertTrue {
                playerCellRepository.playerIncludeCell == null
            }

            // 全身が入ったからnullじゃない
            playerPositionRepository.setPlayerPosition(
                Square(
                    x = 0.5f,
                    y = 0.5f,
                    size = 5f,
                )
            )
            findEventCellUseCase()
            assertTrue {
                playerCellRepository.playerIncludeCell != null
            }
        }
    }
}
