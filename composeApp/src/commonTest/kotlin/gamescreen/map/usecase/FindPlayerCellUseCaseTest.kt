package gamescreen.map.usecase

import gamescreen.map.ModuleMap
import gamescreen.map.data.LoopTestMap
import gamescreen.map.domain.Player
import gamescreen.map.manager.CELL_NUM
import gamescreen.map.manager.SIDE_LENGTH
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.player.PlayerPositionRepository
import gamescreen.map.repository.playercell.PlayerCellRepository
import gamescreen.map.usecase.resetposition.ResetBackgroundPositionUseCase
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
    private val updateCellContainPlayerUseCase: UpdateCellContainPlayerUseCase by inject()

    private val backgroundRepository: BackgroundRepository by inject()
    private val playerCellRepository: PlayerCellRepository by inject()
    private val playerPositionRepository: PlayerPositionRepository by inject()
    private val mapData = LoopTestMap()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleMap
            )
        }

        backgroundRepository.cellNum = CELL_NUM
        backgroundRepository.screenSize = SIDE_LENGTH
        resetBackgroundPositionUseCase.invoke(
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
                Player(size = 5f).moveTo(
                    x = 1f,
                    y = 1f,
                ),
            )

            // 最初に全身が入ってるからnullじゃない
            updateCellContainPlayerUseCase.invoke()
            assertTrue {
                playerCellRepository.eventCell != null
            }

            // 前回のマスから動いてないからnull
            updateCellContainPlayerUseCase.invoke()
            assertTrue {
                playerCellRepository.eventCell == null
            }

            // 全身が入ってないから動いたけどnull
            playerPositionRepository.setPlayerPosition(
                Player(size = 5f).moveTo(
                    x = 6f,
                    y = 6f,
                ),
            )
            updateCellContainPlayerUseCase.invoke()
            assertTrue {
                playerCellRepository.eventCell == null
            }

            // 全身が入ったからnullじゃない
            playerPositionRepository.setPlayerPosition(
                Player(size = 5f).moveTo(
                    x = 0.5f,
                    y = 0.5f,
                ),
            )
            updateCellContainPlayerUseCase.invoke()
            assertTrue {
                playerCellRepository.eventCell != null
            }
        }
    }
}
