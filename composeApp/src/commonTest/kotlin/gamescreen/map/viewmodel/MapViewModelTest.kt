package gamescreen.map.viewmodel

import core.ModuleCore
import core.domain.realm.Position
import data.ModuleData
import gamescreen.ModuleMain
import gamescreen.battle.ModuleBattle
import gamescreen.choice.ModuleChoice
import gamescreen.map.ModuleMap
import gamescreen.map.data.MapData
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Player
import gamescreen.map.repository.encouter.EncounterRepository
import gamescreen.map.repository.position.PositionRepository
import gamescreen.map.usecase.battlenormal.StartNormalBattleUseCase
import gamescreen.map.usecase.move.MoveUseCase
import gamescreen.map.usecase.save.SaveUseCase
import gamescreen.menu.ModuleMenu
import gamescreen.menushop.ModuleShop
import gamescreen.text.ModuleText
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

class MapViewModelTest : KoinTest {
    private val encounterRepository: EncounterRepository by inject()
    private val startNormalBattleUseCase: StartNormalBattleUseCase by inject()
    private val moveUseCase: MoveUseCase by inject()

    private lateinit var mapViewModel: MapViewModel

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                ModuleMain,

                ModuleMap,
                ModuleBattle,
                ModuleMenu,
                ModuleShop,

                ModuleText,
                ModuleChoice,

                ModuleCore,
                ModuleData,
            )
        }

        mapViewModel = MapViewModel(
            encounterRepository = encounterRepository,
            startNormalBattleUseCase = startNormalBattleUseCase,
            positionRepository = object : PositionRepository {
                override fun save(
                    x: Int,
                    y: Int,
                    playerDx: Float,
                    playerDy: Float,
                    objectHeight: ObjectHeight,
                    mapData: MapData,
                ) {

                }

                override fun position(): Position {
                    return Position(
                        objectHeight = ObjectHeight.None
                    )
                }

            },
            moveUseCase = moveUseCase,
            saveUseCase = object : SaveUseCase {
                override fun save(player: Player) {

                }
            }
        )
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

//    @Test
//    fun updateVelocity() {
//        runBlocking {
//            val x = CENTER + 2f
//            val y = CENTER + 1f
//            lateinit var result: Player
//            var count = 0
//            val collectJob = launch {
//                mapViewModel.playerSquare.collect {
//                    count++
//                    result = it
//                }
//            }
//
//            delay(100)
//
//            mapViewModel.setTapPoint(
//                x = x,
//                y = y,
//            )
//
//            mapViewModel.updatePosition()
//
//            delay(100)
//
//            assertEquals(
//                expected = x - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
//                actual = result.square.x,
//            )
//            assertEquals(
//                expected = y - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
//                actual = result.square.y,
//            )
//
//            // todo 回数のテストやる
////            assertEquals(
////                expected = 2,
////                actual = count,
////            )
//
//            collectJob.cancel()
//        }
//    }
//
//    /**
//     * 一回のタップで長く移動
//     */
//    @Test
//    fun updatePosition2Times() {
//        val x = MapViewModel.VIRTUAL_SCREEN_SIZE.toFloat()
//        val y = CENTER
//        var count = 0
//        runBlocking {
//            lateinit var result: Player
//            val collectJob = launch {
//                mapViewModel.playerSquare.collect {
//                    result = it
//                    count++
//                }
//            }
//
//            delay(100)
//
//            mapViewModel.setTapPoint(
//                x = x,
//                y = y,
//            )
//
//            mapViewModel.updatePosition()
//            delay(100)
//
//            assertEquals(
//                CENTER + Velocity.MAX - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
//                result.square.x,
//            )
//            assertEquals(
//                y - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
//                result.square.y,
//            )
//
//            mapViewModel.updatePosition()
//            delay(100)
//
//            assertEquals(
//                CENTER + Velocity.MAX * 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
//                result.square.x,
//            )
//            assertEquals(
//                y - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
//                result.square.y,
//            )
//
//            assertEquals(
//                expected = 3,
//                actual = count,
//            )
//
//            // 値が更新されないことを確認
//            mapViewModel.resetTapPoint()
//            mapViewModel.updatePosition()
//            delay(100)
//
//            assertEquals(
//                CENTER + Velocity.MAX * 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
//                result.square.x,
//            )
//            assertEquals(
//                y - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
//                result.square.y,
//            )
//
//            assertEquals(
//                expected = 3,
//                actual = count,
//            )
//
//            collectJob.cancel()
//        }
//    }

    // fixme プレイヤーの位置の設定をわかりやすくする
    @Test
    fun resetTapPoint() {
        val x = MapViewModel.VIRTUAL_SCREEN_SIZE + 2f
        val y = MapViewModel.VIRTUAL_SCREEN_SIZE + 1f
        var count = 0

        runBlocking {
            lateinit var result: Player
            val collectJob = launch {
                mapViewModel.player.collect {
                    result = it
                    count++
                }
            }

            delay(50)

            mapViewModel.setTapPoint(
                x = x,
                y = y,
            )
            mapViewModel.resetTapPoint()

            mapViewModel.updatePosition()
            delay(100)

            assertEquals(
                MapViewModel.VIRTUAL_SCREEN_SIZE / 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                result.square.x,
            )
            assertEquals(
                MapViewModel.VIRTUAL_SCREEN_SIZE / 2 - MapViewModel.VIRTUAL_PLAYER_SIZE / 2,
                result.square.y,
            )

            // fixme 初期化タイミングが確定したら修正する
//            assertEquals(
//                expected = 3,
//                actual = count,
//            )

            collectJob.cancel()
        }
    }
}
