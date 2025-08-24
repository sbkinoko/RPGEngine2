package gamescreen.map.viewmodel

import common.DefaultScope
import common.FpsCounter
import controller.domain.ControllerCallback
import controller.domain.Stick
import core.domain.mapcell.CellType
import core.repository.memory.screentype.ScreenTypeRepository
import gamescreen.GameScreenType
import gamescreen.map.domain.Player
import gamescreen.map.domain.Point
import gamescreen.map.domain.Velocity
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.encouter.EncounterRepository
import gamescreen.map.repository.playercell.PlayerCellRepository
import gamescreen.map.repository.position.PositionRepository
import gamescreen.map.usecase.PlayerMoveManageUseCase
import gamescreen.map.usecase.battlenormal.StartNormalBattleUseCase
import gamescreen.map.usecase.collision.iscollidedevent.IsCollidedEventUseCase
import gamescreen.map.usecase.event.actionevent.ActionEventUseCase
import gamescreen.map.usecase.event.cellevent.CellEventUseCase
import gamescreen.map.usecase.move.MoveUseCase
import gamescreen.map.usecase.roadmap.RoadMapUseCase
import gamescreen.map.usecase.save.SaveUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.event.EventType

class MapViewModel(
    private val encounterRepository: EncounterRepository,
    private val startNormalBattleUseCase: StartNormalBattleUseCase,

    private val positionRepository: PositionRepository,
    private val saveUseCase: SaveUseCase,

    private val mapUiStateRepository: core.repository.memory.mapuistate.MapUiStateRepository,

    private val moveUseCase: MoveUseCase,
) : ControllerCallback, KoinComponent {
    private val fpsCounter = FpsCounter()
    val fpsFlow = fpsCounter.fpsFlow

    private val playerMoveManageUseCase: PlayerMoveManageUseCase by inject()

    private val screenTypeRepository: ScreenTypeRepository by inject()

    private val backgroundRepository: BackgroundRepository by inject()
    private val playerCellRepository: PlayerCellRepository by inject()

    private val isEventCollidedEventUseCase: IsCollidedEventUseCase by inject()

    private val actionEventUseCase: ActionEventUseCase by inject()
    private val cellEventUseCase: CellEventUseCase by inject()

    private val roadMapUseCase: RoadMapUseCase by inject()

    private var tapPoint: Point? = null

    private var tentativePlayerVelocity: Velocity = Velocity()

    private val canEvent: Boolean
        get() = uiStateFlow.value.player.eventType.canEvent

    val uiStateFlow = mapUiStateRepository.stateFlow

    private var isInBattle = false

    init {
        backgroundRepository.cellNum = CELL_NUM
        backgroundRepository.screenSize = VIRTUAL_SCREEN_SIZE

        DefaultScope.launch {
            val initPlayer = Player(
                size = VIRTUAL_PLAYER_SIZE,
            )

            val data = positionRepository.position()

            roadMapUseCase.invoke(
                mapX = data.mapX,
                mapY = data.mapY,
                mapId = data.mapId,
                playerHeight = data.objectHeight,
                player = initPlayer,
            ).let {
                mapUiStateRepository.updateState(
                    it.copy(
                        player = it.player.copy(
                            square = it.player.square.move(
                                dx = data.playerX,
                                dy = data.playerY,
                            )
                        ),
                    )
                )
            }
        }

        DefaultScope.launch {
            playerCellRepository.playerIncludeCellFlow.collect {
                mapUiStateRepository.updateState(
                    uiStateFlow.value.copy(
                        playerIncludeCell = it,
                    )
                )
            }
        }
    }

    private var autoEvent: EventType? = null

    fun clearFPS() {
        fpsCounter.clear()
    }

    fun measureFPS(time: Long) {
        fpsCounter.addInfo(time)
    }

    /**
     * 主人公の位置を更新
     */
    suspend fun updatePosition() {
        // fixme テストに影響がありそうなので確認する

        // todo FPSが落ちることがあるので確認する
        // タップしていたら位置を更新
        if (tapPoint != null) {
            updateVelocityByTap(tapPoint!!)
        }

        //速度が0なら何もしない
        if (tentativePlayerVelocity.isMoving.not()) {
            return
        }

        val backgroundData = uiStateFlow.value.backgroundData
        val player = uiStateFlow.value.player
        val npcData = uiStateFlow.value.npcData

        val actualVelocity = playerMoveManageUseCase
            .getMovableVelocity(
                tentativePlayerVelocity = tentativePlayerVelocity,
                player = player,
                backgroundData = backgroundData,
                npcData = npcData,
            )

        // 表示物を移動
        mapUiStateRepository.updateState(
            moveUseCase.invoke(
                actualVelocity = actualVelocity,
                tentativeVelocity = tentativePlayerVelocity,
                mapUiState = uiStateFlow.value
            )
        )

        saveUseCase.save(
            player = uiStateFlow.value.player,
        )

        val preEvent = autoEvent
        autoEvent = isEventCollidedEventUseCase.invoke(
            playerSquare = uiStateFlow.value.player.square,
            backgroundData = uiStateFlow.value.backgroundData,
        )

        if (autoEvent != preEvent && autoEvent != null) {
            actionEventUseCase.invoke(
                autoEvent!!,
                mapUiState = uiStateFlow.value,
            ) {
                mapUiStateRepository.updateState(it)
            }
        }

        // fixme moveUseCaseに移動する
        // 水上から陸に上がったとき、そこが移動マスならば、移動を呼び出したい
        //　プレイヤーが今いるマスに基づいてイベントを呼び出し
        playerCellRepository.eventCell?.let { cell ->
            mapUiStateRepository.updateState(
                cellEventUseCase.invoke(
                    cellId = cell.cellType,
                    mapUiState = uiStateFlow.value,
                )
            )
            // 戦闘せずに終了
            return
        }

        // todo 中心が移動した直後に処理を一回呼ぶ
        // マップ外のマスのイベントは全身が入らなくても呼びたい
        val playerCenterCell = playerCellRepository.playerCenterCell

        val monsterCell = playerCenterCell.cellType as? CellType.MonsterCell
            ?: return
        val encounterDistance = actualVelocity.scalar * monsterCell.distanceLate
        if (encounterRepository.judgeStartBattle(
                distance = encounterDistance
            )
        ) {
            startBattle()
        }
    }

    /**
     * @param x tapのx座標
     * @param y tapのy座標
     */
    fun setTapPoint(
        x: Float,
        y: Float,
    ) {
        tapPoint = Point(
            x = x,
            y = y,
        )
    }

    /**
     * タップの位置に対して速度を計算
     */
    private fun updateVelocityByTap(tapPoint: Point) {
        val square = uiStateFlow.value.player.square
        val size = uiStateFlow.value.player.size
        val dx = (tapPoint.x) - (square.x + size / 2)
        val dy = (tapPoint.y) - (square.y + size / 2)
        updateTentativeVelocity(
            velocity = Velocity(
                x = dx,
                y = dy,
            )
        )
    }

    private fun updateVelocityByStick(
        dx: Float,
        dy: Float,
    ) {
        val player = uiStateFlow.value.player
        val vx = player.maxVelocity * dx
        val vy = player.maxVelocity * dy
        updateTentativeVelocity(
            velocity = Velocity(
                x = vx,
                y = vy,
            )
        )
    }

    private fun updateTentativeVelocity(
        velocity: Velocity,
    ) {
        if (isInBattle) {
            return
        }

        tentativePlayerVelocity = velocity
    }

    /**
     * タップしてない状態にする
     */
    fun resetTapPoint() {
        val velocity = Velocity(
            x = 0f,
            y = 0f,
        )
        tapPoint = null

        tentativePlayerVelocity = velocity
    }

    private fun event() {
        actionEventUseCase.invoke(
            eventType = uiStateFlow.value.player.eventType,
            mapUiState = uiStateFlow.value,
        ) {
            mapUiStateRepository.updateState(it)
        }
    }

    fun touchCharacter() {
        if (canEvent) {
            event()
        } else {
            showMenu()
        }
    }

    fun touchEventSquare() {
        if (canEvent) {
            event()
        }
    }

    override fun pressA() {
        if (canEvent) {
            event()
        }
    }

    override fun pressB() {
        startBattle()
    }

    private fun startBattle() {
        // バトル中フラグを立てる
        isInBattle = true

        // タップ状態解除
        resetTapPoint()

        startNormalBattleUseCase.invoke(
            mapUiState = uiStateFlow.value
        ) {
            // バトルフラグをおろす
            isInBattle = false

            // 再開場所の情報を受け取る
            mapUiStateRepository.updateState(it)
        }
    }

    override fun pressM() {
        showMenu()
    }

    private fun showMenu() {
        screenTypeRepository.setScreenType(
            gameScreenType = GameScreenType.MENU
        )
    }

    override fun moveStick(
        stick: Stick,
    ) {
        updateVelocityByStick(
            dx = stick.x.toFloat(),
            dy = stick.y.toFloat(),
        )
    }

    companion object {
        const val MOVE_BORDER = 0.3f
        const val VIRTUAL_SCREEN_SIZE = 210
        const val VIRTUAL_PLAYER_SIZE = 20f
        const val CELL_NUM = 5

        const val CELL_SIZE = VIRTUAL_SCREEN_SIZE / CELL_NUM.toFloat()
    }
}
