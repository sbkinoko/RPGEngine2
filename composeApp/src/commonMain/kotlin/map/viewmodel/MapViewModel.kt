package map.viewmodel

import controller.domain.ControllerCallback
import controller.domain.StickPosition
import core.domain.ScreenType
import core.repository.screentype.ScreenTypeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import map.data.LoopMap
import map.data.NonLoopMap
import map.domain.BackgroundCell
import map.domain.MapData
import map.domain.Player
import map.domain.Point
import map.domain.Velocity
import map.domain.collision.Square
import map.layout.PlayerMoveSquare
import map.repository.backgroundcell.BackgroundRepository
import map.repository.player.PlayerRepository
import map.repository.playercell.PlayerCellRepository
import map.usecase.FindEventCellUseCase
import map.usecase.GetScreenCenterUseCase
import map.usecase.IsCollidedUseCase
import map.usecase.MoveBackgroundUseCase
import map.usecase.PlayerMoveManageUseCase
import map.usecase.PlayerMoveToUseCase
import map.usecase.PlayerMoveUseCase
import map.usecase.ResetBackgroundPositionUseCase
import map.usecase.VelocityManageUseCase
import map.usecase.startbattle.StartBattleUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MapViewModel : ControllerCallback, KoinComponent {
    val player: Player by inject()
    private val playerRepository: PlayerRepository by inject()
    private val playerMoveManageUseCase: PlayerMoveManageUseCase by inject()
    private val velocityManageUseCase: VelocityManageUseCase by inject()

    private val screenTypeRepository: ScreenTypeRepository by inject()

    private val getScreenCenterUseCase: GetScreenCenterUseCase by inject()
    private val playerMoveUseCase: PlayerMoveUseCase by inject()
    private val playerMoveToUseCase: PlayerMoveToUseCase by inject()

    private val isCollidedUseCase: IsCollidedUseCase by inject()
    private val moveBackgroundUseCase: MoveBackgroundUseCase by inject()
    private val resetBackgroundPositionUseCase: ResetBackgroundPositionUseCase by inject()
    private val backgroundRepository: BackgroundRepository by inject()
    private val playerCellRepository: PlayerCellRepository by inject()

    private val findEventCellUseCase: FindEventCellUseCase by inject()
    private val startBattleUseCase: StartBattleUseCase by inject()

    val playerSquare: SharedFlow<Square> = playerRepository.playerPositionFLow

    private var tapPoint: Point? = null

    private var playerMoveArea: Square

    private var backGroundVelocity: Velocity = Velocity()
    private var tentativePlayerVelocity: Velocity = Velocity()

    val backgroundCells = backgroundRepository.backgroundFlow

    init {
        playerMoveArea = PlayerMoveSquare(
            screenSize = VIRTUAL_SCREEN_SIZE,
            borderRate = MOVE_BORDER,
        )
        backgroundRepository.cellNum = 5
        backgroundRepository.screenSize = VIRTUAL_SCREEN_SIZE

        CoroutineScope(Dispatchers.Default).launch {
            playerRepository.setPlayerPosition(
                Square(size = player.size)
            )
        }

        reloadMapData(
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
        if (tapPoint != null) {
            updateVelocityByTap(tapPoint!!)
        }

        if (tentativePlayerVelocity.isMoving) {
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

            // playerが入っているマスを設定
            findEventCellUseCase()
            //　そのマスに基づいてイベントを呼び出し
            playerCellRepository.playerIncludeCell?.let {
                callCellEvent(it)
            }
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
        val square = playerRepository.getPlayerPosition()
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

    /**
     * プレイヤーを中心に移動する
     */
    private fun setPlayerCenter() {
        val center = getScreenCenterUseCase()
        // 仮の移動先
        CoroutineScope(Dispatchers.Default).launch {
            playerMoveToUseCase(
                x = center.x - player.size / 2,
                y = center.y - player.size / 2,
            )
        }
    }

    /**
     * 全身が入ったセルのイベントを処理する
     */
    private fun callCellEvent(backgroundCell: BackgroundCell) {
        when (backgroundCell.imgID) {
            3 -> {
                backgroundRepository.mapData = NonLoopMap()
                reloadMapData(
                    mapX = 0,
                    mapY = 2,
                    mapData = NonLoopMap(),
                )
            }

            4 -> {
                reloadMapData(
                    mapX = 5,
                    mapY = 5,
                    mapData = LoopMap()
                )
            }
        }
    }

    /**
     * 中心を指定して背景画像を再度読み込む
     */
    private fun reloadMapData(
        mapX: Int,
        mapY: Int,
        mapData: MapData,
    ) {
        setPlayerCenter()
        resetBackgroundPositionUseCase(
            mapData = mapData,
            mapX = mapX,
            mapY = mapY,
        )
        findEventCellUseCase()
    }

    private var canMove = true
    private fun checkMove() {
        val square = playerRepository.getPlayerPosition().getNew()
        square.move(
            dx = tentativePlayerVelocity.x,
            dy = tentativePlayerVelocity.y
        )

        // このままの速度で動けるなら移動
        if (isCollidedUseCase(square).not()) {
            return
        }

        // 動けないので動ける最大の速度を取得
        tentativePlayerVelocity = playerMoveManageUseCase.getMovableVelocity(
            tentativePlayerVelocity = tentativePlayerVelocity,
        )
    }

    fun getAroundCellId(x: Int, y: Int): Array<Array<Int>> {
        return backgroundRepository.getBackgroundAround(
            x = x,
            y = y,
        )
    }

    override fun pressA() {
        //todo Aを押した時の処理を実装
    }

    override fun pressB() {
        startBattleUseCase()
    }

    override fun pressM() {
        screenTypeRepository.screenType = ScreenType.MENU
    }

    override fun moveStick(
        stickPosition: StickPosition
    ) {
        updateVelocityByStick(
            dx = stickPosition.x.toFloat(),
            dy = stickPosition.y.toFloat(),
        )
    }

    companion object {
        const val MOVE_BORDER = 0.3f
        const val VIRTUAL_SCREEN_SIZE = 210
        const val VIRTUAL_PLAYER_SIZE = 20f
    }
}
