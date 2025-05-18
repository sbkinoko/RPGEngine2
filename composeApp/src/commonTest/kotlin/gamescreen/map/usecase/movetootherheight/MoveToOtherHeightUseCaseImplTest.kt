package gamescreen.map.usecase.movetootherheight

import core.domain.mapcell.CellType
import core.domain.testMapUiState
import gamescreen.map.ModuleMap
import gamescreen.map.data.MapData
import gamescreen.map.domain.MapUiState
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Player
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.npc.NPC
import gamescreen.map.domain.npc.NPCData
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
import kotlin.test.assertTrue

class MoveToOtherHeightUseCaseImplTest : KoinTest {
    private val initPlayer = Player(
        // セルの大きさが画面の1/3
        //　その半分より小さければいいので1/6より小さければOK
        // 適当に1/10を採用
        size = MapViewModel.VIRTUAL_SCREEN_SIZE / 10.toFloat()
    )

    private
    val moveToOtherHeightUseCase: MoveToOtherHeightUseCase by inject()

    private val restBackgroundPositionUseCase: ResetBackgroundPositionUseCase by inject()

    private val backgroundRepository: BackgroundRepository by inject()

    private lateinit var backgroundData: BackgroundData

    private val npcData = NPCData(emptyList())

    val uiState: MapUiState
        get() = testMapUiState.copy(
            backgroundData = backgroundData,
            player = initPlayer,
            npcData = npcData,
        )

    private val mapData = object : MapData() {
        override val isLoop: Boolean
            get() = false
        override val width: Int
            get() = 3
        override val height: Int
            get() = 3
        override val field: Array<Array<CellType>>
            get() = arrayOf(
                arrayOf(
                    CellType.Glass,
                    CellType.Glass,
                    CellType.Glass,
                ),
                arrayOf(
                    CellType.Glass,
                    CellType.Water,
                    CellType.Glass,
                ),
                arrayOf(
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
            backgroundRepository.cellNum = 3

            backgroundData = restBackgroundPositionUseCase.invoke(
                mapData = mapData,
                mapX = 1,
                mapY = 1,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    /**
     * プレイヤーの高さが変わることを確認
     */
    @Test
    fun isInWater() {
        runBlocking {
            val targetHeight = ObjectHeight.Water(1)
            moveToOtherHeightUseCase.invoke(
                targetHeight,
                uiState,
            ) {

            }

            // todo 高さを外に出せるようになったら修正
//            assertEquals(
//                expected = targetHeight,
//                actual = playerPositionRepository.getPlayerPosition().square.objectHeight
//            )
        }
    }

    /**
     * プレイヤーの高さが変わることを確認
     */
    @Test
    fun isInGround() {
        runBlocking {
            val targetHeight = ObjectHeight.Ground(1)
            moveToOtherHeightUseCase.invoke(
                targetHeight,
                uiState,
            ) {

            }

            // todo 高さを外に出せるようになったら修正
//            assertEquals(
//                expected = targetHeight,
//                actual = playerPositionRepository.getPlayerPosition().square.objectHeight
//            )
        }
    }

    /**
     * 水の左上から右下へ水の中へ移動することを確認
     */
    @Test
    fun leftTopToRight() {
        runBlocking {
            val player = initPlayer.run {
                copy(
                    square = square.moveTo(
                        x = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 - square.width,
                        y = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 - square.height / 2,
                    ),
                    tentativeVelocity = Velocity(
                        x = 1f,
                    ),
                )
            }

            moveToOtherHeightUseCase.invoke(
                ObjectHeight.Water(1),
                uiState.copy(
                    player = player
                ),
            ) {

            }

            // todo 高さを外に出せるようになったら修正
//            checkInWater()
        }
    }

    /**
     * 水の左から右へ水の中へ移動することを確認
     */
    @Test
    fun leftToRight() {
        runBlocking {
            val player = initPlayer.run {
                copy(
                    square = square.moveTo(
                        x = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 - square.width,
                        y = MapViewModel.VIRTUAL_SCREEN_SIZE / 2 - square.height / 2,
                    ),
                    tentativeVelocity = Velocity(
                        x = 1f,
                    ),
                )
            }

            moveToOtherHeightUseCase.invoke(
                ObjectHeight.Water(1),
                uiState.copy(
                    player = player
                )
            ) {

            }

// todo 高さを外に出せるようになったら修正
//            checkInWater()
        }
    }

    /**
     * 水の左から右上へ水の中へ移動することを確認
     */
    @Test
    fun leftBottomToRight() {
        runBlocking {
            val player = initPlayer.run {
                copy(
                    square = square.moveTo(
                        x = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 - square.width,
                        y = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 * 2 - square.height / 2,
                    ),
                    tentativeVelocity = Velocity(
                        x = 1f,
                    ),
                )
            }

            moveToOtherHeightUseCase.invoke(
                ObjectHeight.Water(1),
                uiState.copy(
                    player = player
                )
            ) {

            }
// todo 高さを外に出せるようになったら修正
//            checkInWater()
        }
    }

    /**
     * 水の左下から右上へ水の中へ移動することを確認
     */
    @Test
    fun bottomLeftToTop() {
        runBlocking {
            val player = initPlayer.run {
                copy(
                    square = square.moveTo(
                        x = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 - square.width / 2,
                        y = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 * 2.toFloat(),
                    ),
                    tentativeVelocity = Velocity(
                        y = -1f,
                    ),
                )
            }

            moveToOtherHeightUseCase.invoke(
                ObjectHeight.Water(1),
                uiState.copy(
                    player = player
                )
            ) {

            }

// todo 高さを外に出せるようになったら修正
//            checkInWater()
        }
    }

    /**
     * 水の下から上へ水の中へ移動することを確認
     */
    @Test
    fun bottomToTop() {
        runBlocking {
            val player = initPlayer.run {
                copy(
                    square = square.moveTo(
                        x = MapViewModel.VIRTUAL_SCREEN_SIZE / 2 - square.width / 2,
                        y = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 * 2.toFloat(),
                    ),
                    tentativeVelocity = Velocity(
                        y = -1f,
                    ),
                )
            }

            moveToOtherHeightUseCase.invoke(
                ObjectHeight.Water(1),
                uiState.copy(
                    player = player
                )
            ) {

            }

            // todo 高さを外に出せるようになったら修正
//            checkInWater()
        }
    }

    /**
     * 水の右下から左上へ水の中へ移動することを確認
     */
    @Test
    fun bottomRightToTop() {
        runBlocking {
            val player = initPlayer.run {
                copy(
                    square = square.moveTo(
                        x = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 * 2 - square.width / 2,
                        y = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 * 2.toFloat(),
                    ),
                    tentativeVelocity = Velocity(
                        y = -1f,
                    ),
                )
            }

            moveToOtherHeightUseCase.invoke(
                ObjectHeight.Water(1),
                uiState.copy(
                    player = player
                )
            ) {

            }

            // todo 高さを外に出せるようになったら修正
//            checkInWater()
        }
    }

    /**
     * 水の右下から左上へ水の中へ移動することを確認
     */
    @Test
    fun rightBottomToLeft() {
        runBlocking {
            val player = initPlayer.run {
                copy(
                    square = square.moveTo(
                        x = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 * 2.toFloat(),
                        y = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 * 2 - square.height / 2,
                    ),
                    tentativeVelocity = Velocity(
                        x = -1f,
                    ),
                )
            }

            moveToOtherHeightUseCase.invoke(
                ObjectHeight.Water(1),
                uiState.copy(
                    player = player
                )
            ) {

            }

// todo 高さを外に出せるようになったら修正
//            checkInWater()
        }
    }

    /**
     * 水の右から左へ水の中へ移動することを確認
     */
    @Test
    fun rightToLeft() {
        runBlocking {
            val player = initPlayer.run {
                copy(
                    square = square.moveTo(
                        x = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 * 2.toFloat(),
                        y = MapViewModel.VIRTUAL_SCREEN_SIZE / 2 - square.height / 2,
                    ),
                    tentativeVelocity = Velocity(
                        x = -1f,
                    ),
                )
            }

            moveToOtherHeightUseCase.invoke(
                ObjectHeight.Water(1),
                uiState.copy(
                    player = player
                )
            ) {

            }

            // todo 高さを外に出せるようになったら修正
//            checkInWater()
        }
    }

    /**
     * 水の右上から左下へ水の中へ移動することを確認
     */
    @Test
    fun rightTopToLeft() {
        runBlocking {
            val player = initPlayer.run {
                copy(
                    square = square.moveTo(
                        x = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 * 2.toFloat(),
                        y = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 - square.height / 2,
                    ),
                    tentativeVelocity = Velocity(
                        x = -1f,
                    ),
                )
            }

            moveToOtherHeightUseCase.invoke(
                ObjectHeight.Water(1),
                uiState.copy(
                    player = player
                )
            ) {

            }

// todo 高さを外に出せるようになったら修正
//            checkInWater()
        }
    }

    /**
     * 水の右上から左下へ水の中へ移動することを確認
     */
    @Test
    fun topRightToBottom() {
        runBlocking {
            val player = initPlayer.run {
                copy(
                    square = square.moveTo(
                        x = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 * 2.toFloat() - square.width / 2,
                        y = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 - square.height,
                    ),
                    tentativeVelocity = Velocity(
                        y = 1f,
                    ),
                )
            }

            moveToOtherHeightUseCase.invoke(
                ObjectHeight.Water(1),
                uiState.copy(
                    player = player
                )
            ) {

            }

            // todo 高さを外に出せるようになったら修正
//            checkInWater()
        }
    }

    /**
     * 水の上から下へ水の中へ移動することを確認
     */
    @Test
    fun topToBottom() {
        runBlocking {
            val player = initPlayer.run {
                copy(
                    square = square.moveTo(
                        x = MapViewModel.VIRTUAL_SCREEN_SIZE / 2 - square.width / 2,
                        y = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 - square.height,
                    ),
                    tentativeVelocity = Velocity(
                        y = 1f,
                    ),
                )
            }

            moveToOtherHeightUseCase.invoke(
                ObjectHeight.Water(1),
                uiState.copy(
                    player = player
                )
            ) {

            }

            // todo 高さを外に出せるようになったら修正
//            checkInWater()
        }
    }

    /**
     * 水の右上から左下へ水の中へ移動することを確認
     */
    @Test
    fun topLeftToBottom() {
        runBlocking {
            val player = initPlayer.run {
                copy(
                    square = square.moveTo(
                        x = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 - square.width / 2,
                        y = MapViewModel.VIRTUAL_SCREEN_SIZE / 3 - square.height,
                    ),
                    tentativeVelocity = Velocity(
                        y = 1f,
                    ),
                )
            }

            moveToOtherHeightUseCase.invoke(
                ObjectHeight.Water(1),
                uiState.copy(
                    player = player
                )
            ) {

            }
// todo 高さを外に出せるようになったら修正
//            checkInWater()
        }
    }

    private fun checkInWater(
        player: Player,
    ) {
        // fixme playerを
        val square = player.square

        assertTrue {
            MapViewModel.VIRTUAL_SCREEN_SIZE / 3 <= square.leftSide &&
                    square.rightSide <= MapViewModel.VIRTUAL_SCREEN_SIZE / 3 * 2
        }
        assertTrue {
            MapViewModel.VIRTUAL_SCREEN_SIZE / 3 <= square.topSide &&
                    square.bottomSide <= MapViewModel.VIRTUAL_SCREEN_SIZE / 3 * 2
        }
    }
}
