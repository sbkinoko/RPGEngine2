package gamescreen.map.usecase.collision.iscollided

import core.domain.mapcell.CellType
import gamescreen.map.ModuleMap
import gamescreen.map.data.MapData
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Player
import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.domain.npc.NPC
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.usecase.resetposition.ResetBackgroundPositionUseCase
import gamescreen.map.viewmodel.MapViewModel
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IsCollidedUseCaseTest : KoinTest {

    private val isCollidedUseCase: IsCollidedUseCase by inject()

    private val restBackgroundPositionUseCase: ResetBackgroundPositionUseCase by inject()

    private val backgroundRepository: BackgroundRepository by inject()

    private val mapData = object : MapData() {
        override val isLoop: Boolean
            get() = false
        override val width: Int
            get() = 5
        override val height: Int
            get() = 5
        override val field: Array<Array<CellType>>
            get() = arrayOf(
                arrayOf(
                    CellType.Glass,
                    CellType.Glass,
                    CellType.Glass,
                    CellType.Glass,
                    CellType.Glass,
                ),
                arrayOf(
                    CellType.Glass,
                    CellType.Glass,
                    CellType.Water,
                    CellType.Glass,
                    CellType.Glass,
                ),
                arrayOf(
                    CellType.Glass,
                    CellType.Water,
                    CellType.Water,
                    CellType.Water,
                    CellType.Glass,
                ),
                arrayOf(
                    CellType.Glass,
                    CellType.Glass,
                    CellType.Water,
                    CellType.Glass,
                    CellType.Glass,
                ),
                arrayOf(
                    CellType.Glass,
                    CellType.Glass,
                    CellType.Glass,
                    CellType.Glass,
                    CellType.Glass,
                ),
            )
        override val npcList: List<NPC>
            get() = emptyList()

    }

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleMap,
            )
        }

        runBlocking {
            backgroundRepository.cellNum = 5

            restBackgroundPositionUseCase.invoke(
                mapData = mapData,
                mapX = 2,
                mapY = 2,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
        backgroundRepository.cellNum = 3
    }

    @Test
    fun collideWithWater() {
        val player = Player(
            size = MapViewModel.VIRTUAL_PLAYER_SIZE,
        ).moveTo(
            x = MapViewModel.VIRTUAL_SCREEN_SIZE.toFloat() / 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
            y = MapViewModel.VIRTUAL_SCREEN_SIZE.toFloat() / 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
        )

        assertTrue {
            isCollidedUseCase.invoke(
                playerSquare = player.square
            )
        }
    }


    @Test
    fun collideWithWaterAndWater() {
        val player = Player(
            size = MapViewModel.VIRTUAL_PLAYER_SIZE,
        ).moveTo(
            x = MapViewModel.VIRTUAL_SCREEN_SIZE.toFloat() / 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
            y = MapViewModel.VIRTUAL_SCREEN_SIZE.toFloat() / 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
        )

        val waterPlayer = player.copy(
            square = (player.square as NormalSquare).copy(
                objectHeight = ObjectHeight.Water,
            )
        )

        assertFalse {
            isCollidedUseCase.invoke(
                playerSquare = waterPlayer.square
            )
        }
    }

    @Test
    fun collideWithGround() {
        val player = Player(
            size = MapViewModel.VIRTUAL_PLAYER_SIZE,
        ).moveTo(
            x = 0f,
            y = 0f,
        )

        assertFalse {
            isCollidedUseCase.invoke(
                playerSquare = player.square
            )
        }
    }
}
