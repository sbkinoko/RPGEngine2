package gamescreen.map.viewmodel

import controller.domain.ControllerCallback
import controller.domain.Stick
import core.domain.ScreenType
import core.domain.mapcell.CellType
import core.repository.screentype.ScreenTypeRepository
import gamescreen.map.data.LoopMap
import gamescreen.map.domain.Player
import gamescreen.map.domain.PlayerDir
import gamescreen.map.domain.Point
import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.Square
import gamescreen.map.domain.toDir
import gamescreen.map.layout.PlayerMoveSquare
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.map.repository.player.PlayerPositionRepository
import gamescreen.map.repository.playercell.PlayerCellRepository
import gamescreen.map.usecase.MoveBackgroundUseCase
import gamescreen.map.usecase.PlayerMoveManageUseCase
import gamescreen.map.usecase.PlayerMoveUseCase
import gamescreen.map.usecase.UpdateCellContainPlayerUseCase
import gamescreen.map.usecase.VelocityManageUseCase
import gamescreen.map.usecase.collision.geteventtype.GetEventTypeUseCase
import gamescreen.map.usecase.collision.iscollided.IsCollidedUseCase
import gamescreen.map.usecase.event.actionevent.ActionEventUseCase
import gamescreen.map.usecase.event.cellevent.CellEventUseCase
import gamescreen.map.usecase.roadmap.RoadMapUseCase
import gamescreen.map.usecase.startbattle.StartBattleUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import values.EventType

class MapViewModel : ControllerCallback, KoinComponent {
    val player: Player by inject()
    private val playerPositionRepository: PlayerPositionRepository by inject()
    private val playerMoveManageUseCase: PlayerMoveManageUseCase by inject()
    private val velocityManageUseCase: VelocityManageUseCase by inject()

    private val screenTypeRepository: ScreenTypeRepository by inject()

    private val playerMoveUseCase: PlayerMoveUseCase by inject()

    private val isCollidedUseCase: IsCollidedUseCase by inject()
    private val getEventTypeUseCase: GetEventTypeUseCase by inject()

    private val moveBackgroundUseCase: MoveBackgroundUseCase by inject()
    private val backgroundRepository: BackgroundRepository by inject()
    private val playerCellRepository: PlayerCellRepository by inject()

    private val updateCellContainPlayerUseCase: UpdateCellContainPlayerUseCase by inject()
    private val startBattleUseCase: StartBattleUseCase by inject()
    private val actionEventUseCase: ActionEventUseCase by inject()
    private val cellEventUseCase: CellEventUseCase by inject()

    private val roadMapUseCase: RoadMapUseCase by inject()

    // fixme stateFlowにする
    val playerSquare: SharedFlow<Square> = playerPositionRepository.playerPositionFLow

    private var eventSquare: Square = Square(
        size = VIRTUAL_PLAYER_SIZE,
        displayPoint = Point(
            x = playerPositionRepository.getPlayerPosition().x,
            y = playerPositionRepository.getPlayerPosition().y
        ),
    )
        set(value) {
            mutableEventSquareFlow.value = value
            field = value
        }

    private val mutableEventSquareFlow = MutableStateFlow(
        eventSquare
    )
    val eventSquareFlow: StateFlow<Square> = mutableEventSquareFlow.asStateFlow()

    private var tapPoint: Point? = null

    private var playerMoveArea: Square = PlayerMoveSquare(
        screenSize = VIRTUAL_SCREEN_SIZE,
        borderRate = MOVE_BORDER,
    )

    private var backGroundVelocity: Velocity = Velocity()
    private var tentativePlayerVelocity: Velocity = Velocity()

    private var dir: PlayerDir = PlayerDir.DOWN
        set(value) {
            field = value
            mutableDirFlow.value = dir
        }
    private val mutableDirFlow = MutableStateFlow<PlayerDir>(
        dir
    )
    val dirFlow: StateFlow<PlayerDir> = mutableDirFlow.asStateFlow()

    private var eventType: EventType = EventType.None
        set(value) {
            mutableEventTypeFlow.value = value
            field = value
        }
    private val mutableEventTypeFlow = MutableStateFlow(
        eventType
    )
    val eventTypeFlow: StateFlow<EventType> = mutableEventTypeFlow.asStateFlow()

    private val canEvent: Boolean
        get() = eventType != EventType.None


    val backgroundCells = backgroundRepository.backgroundFlow

    init {
        backgroundRepository.cellNum = 5
        backgroundRepository.screenSize = VIRTUAL_SCREEN_SIZE

        CoroutineScope(Dispatchers.Default).launch {
            playerPositionRepository.setPlayerPosition(
                Square(size = player.size)
            )
        }

        roadMapUseCase.invoke(
            mapX = 0,
            mapY = 0,
            mapData = LoopMap(),
        )
    }

    private val fieldSquare: Square = Square(
        x = 0f,
        y = 0f,
        size = VIRTUAL_SCREEN_SIZE.toFloat(),
    )

    /**
     * 主人公の位置を更新
     */
    fun updatePosition() {
        // タップしていたら位置を更新
        if (tapPoint != null) {
            updateVelocityByTap(tapPoint!!)
        }

        //速度が0なら何もしない
        if (tentativePlayerVelocity.isMoving.not()) {
            return
        }

        dir = tentativePlayerVelocity.toDir()

        checkMove()
        if (!canMove) {
            return
        }

        mediateVelocity()
        CoroutineScope(Dispatchers.Default).launch {
            playerMoveUseCase(
                player = player,
            )
        }
        moveBackgroundUseCase(
            velocity = backGroundVelocity,
            fieldSquare = fieldSquare,
        )

        updateEventCollision()
        checkEvent()
        // playerが入っているマスを設定
        updateCellContainPlayerUseCase()
        //　そのマスに基づいてイベントを呼び出し
        playerCellRepository.playerIncludeCell?.let {
            cellEventUseCase.invoke(
                it.cellType,
            )
        }
    }

    /**
     * イベントの当たり判定を移動させる
     */
    private fun updateEventCollision() {
        when (dir) {
            PlayerDir.UP -> {
                eventSquare = Square(
                    size = VIRTUAL_PLAYER_SIZE,
                    displayPoint = Point(
                        x = playerPositionRepository.getPlayerPosition().x,
                        y = playerPositionRepository.getPlayerPosition().y - VIRTUAL_PLAYER_SIZE / 2
                    ),
                )
            }

            PlayerDir.DOWN -> {
                eventSquare = Square(
                    size = VIRTUAL_PLAYER_SIZE,
                    displayPoint = Point(
                        x = playerPositionRepository.getPlayerPosition().x,
                        y = playerPositionRepository.getPlayerPosition().y + VIRTUAL_PLAYER_SIZE / 2
                    ),
                )
            }

            PlayerDir.LEFT -> {
                eventSquare = Square(
                    size = VIRTUAL_PLAYER_SIZE,
                    displayPoint = Point(
                        x = playerPositionRepository.getPlayerPosition().x - VIRTUAL_PLAYER_SIZE / 2,
                        y = playerPositionRepository.getPlayerPosition().y
                    ),
                )
            }

            PlayerDir.RIGHT -> {
                eventSquare = Square(
                    size = VIRTUAL_PLAYER_SIZE,
                    displayPoint = Point(
                        x = playerPositionRepository.getPlayerPosition().x + VIRTUAL_PLAYER_SIZE / 2,
                        y = playerPositionRepository.getPlayerPosition().y
                    ),
                )
            }

            PlayerDir.NONE -> Unit
        }
    }

    private fun checkEvent() {
        eventType = getEventTypeUseCase.invoke(
            eventSquare
        )
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
        val square = playerPositionRepository.getPlayerPosition()
        val dx = (tapPoint.x) - (square.x + player.size / 2)
        val dy = (tapPoint.y) - (square.y + player.size / 2)
        tentativePlayerVelocity = Velocity(
            x = dx,
            y = dy,
        )
    }

    private fun updateVelocityByStick(dx: Float, dy: Float) {
        val vx = player.maxVelocity * dx
        val vy = player.maxVelocity * dy
        tentativePlayerVelocity = Velocity(
            x = vx,
            y = vy,
        )
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

    /**
     * playerを動かすか、背景を動かすか決定する
     */
    private fun mediateVelocity() {
        val mediatedVelocity = velocityManageUseCase.manageVelocity(
            tentativePlayerVelocity = tentativePlayerVelocity,
            playerMoveArea = playerMoveArea,
        )

        player.updateVelocity(mediatedVelocity.first)

        backGroundVelocity = mediatedVelocity.second
    }

    private var canMove = true

    private fun checkMove() {
        val square = playerPositionRepository.getPlayerPosition().getNew()
        square.move(
            dx = tentativePlayerVelocity.x,
            dy = tentativePlayerVelocity.y
        )

        // このままの速度で動けるなら移動
        if (isCollidedUseCase.invoke(square).not()) {
            return
        }

        // 動けないので動ける最大の速度を取得
        tentativePlayerVelocity = playerMoveManageUseCase.getMovableVelocity(
            tentativePlayerVelocity = tentativePlayerVelocity,
        )
    }

    fun getAroundCellId(x: Int, y: Int): Array<Array<CellType>> {
        return backgroundRepository.getBackgroundAround(
            x = x,
            y = y,
        )
    }

    private fun event() {
        actionEventUseCase.invoke(eventType)
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
        startBattleUseCase()
    }

    override fun pressM() {
        showMenu()
    }

    private fun showMenu() {
        screenTypeRepository.screenType = ScreenType.MENU
    }

    override fun moveStick(
        stick: Stick
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
    }
}
