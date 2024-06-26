package map.viewmodel

import controller.domain.ControllerCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import map.data.LoopMap
import map.data.NonLoopMap
import map.domain.BackgroundCell
import map.domain.Player
import map.domain.Point
import map.domain.Square
import map.domain.Velocity
import map.domain.VelocityManager
import map.layout.PlayerMoveSquare
import map.manager.BackgroundManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MapViewModel : ControllerCallback, KoinComponent {
    val player: Player by inject()

    private val mutablePlayerPosition: MutableStateFlow<Square>
    val playerPosition: StateFlow<Square>

    private var tapPoint: Point? = null

    private var mutableBackgroundManager:
            MutableStateFlow<BackgroundManager>

    val backgroundManger: StateFlow<BackgroundManager>
        get() = mutableBackgroundManager.asStateFlow()

    private var playerMoveArea: Square

    private var backGroundVelocity: Velocity = Velocity()
    private var tentativePlayerVelocity: Velocity = Velocity()

    override lateinit var pressB: () -> Unit


    init {
        mutablePlayerPosition = MutableStateFlow(player.square)
        playerPosition = mutablePlayerPosition.asStateFlow()

        mutableBackgroundManager = MutableStateFlow(
            BackgroundManager(
                cellNum = 5,
                sideLength = VIRTUAL_SCREEN_SIZE,
            )
        )
        backgroundManger.value.setMapData(LoopMap())

        playerMoveArea = PlayerMoveSquare(
            screenSize = VIRTUAL_SCREEN_SIZE,
            borderRate = MOVE_BORDER,
        )

        reloadMapData(mapX = 0, mapY = 0)
    }

    /**
     * 主人公の位置を更新
     */
    fun updatePosition() {
        if (tapPoint != null) {
            updateVelocityByTap(tapPoint!!)
        }

        if (tentativePlayerVelocity.isMoving) {
            mediateVelocity()

            player.move()
            // todo いい感じにする方法を探す
            mutablePlayerPosition.value = player.square.getNew()
            mutableBackgroundManager.value.moveBackgroundCell(
                velocity = backGroundVelocity
            )
            backgroundManger.value.apply {
                findCellIncludePlayer(
                    playerSquare = player.square
                )
                // イベントがあったらイベント処理をする
                eventCell?.apply {
                    callCellEvent(this)
                }
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
        val dx = (tapPoint.x) - (player.square.x + player.size / 2)
        val dy = (tapPoint.y) - (player.square.y + player.size / 2)
        tentativePlayerVelocity = Velocity(
            dx = dx,
            dy = dy,
        )
    }

    private fun updateVelocityByStick(dx: Float, dy: Float) {
        val vx = player.maxVelocity * dx
        val vy = player.maxVelocity * dy

        tentativePlayerVelocity = Velocity(
            dx = vx,
            dy = vy,
        )
    }

    /**
     * タップしてない状態にする
     */
    fun resetTapPoint() {
        val velocity = Velocity(
            dx = 0f,
            dy = 0f,
        )
        tapPoint = null

        tentativePlayerVelocity = velocity
    }

    /**
     * playerを動かすか、背景を動かすか決定する
     */
    private fun mediateVelocity() {
        val mediatedVelocity = VelocityManager.manageVelocity(
            tentativePlayerVelocity = tentativePlayerVelocity,
            player = player.square,
            playerMoveArea = playerMoveArea,
        )
        player.updateVelocity(mediatedVelocity.first)
        backGroundVelocity = mediatedVelocity.second
    }

    /**
     * プレイヤーを中心に移動する
     */
    private fun setPlayerCenter() {
        val center = backgroundManger.value.getCenterOfDisplay()
        // 仮の移動先
        player.moveTo(
            center,
        )
    }

    /**
     * 全身が入ったセルのイベントを処理する
     */
    private fun callCellEvent(backgroundCell: BackgroundCell) {
        when (backgroundCell.imgID) {
            3 -> {
                backgroundManger.value.setMapData(NonLoopMap())
                reloadMapData(
                    mapX = 0,
                    mapY = 2,
                )
            }

            4 -> {
                backgroundManger.value.setMapData(LoopMap())
                reloadMapData(
                    mapX = 5,
                    mapY = 5,
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
    ) {
        setPlayerCenter()
        backgroundManger.value.resetBackgroundCellPosition(
            mapX = mapX,
            mapY = mapY,
        )
        backgroundManger.value.findCellIncludePlayer(
            playerSquare = player.square
        )
    }

    override fun moveStick(
        dx: Float,
        dy: Float,
    ) {
        updateVelocityByStick(
            dx = dx,
            dy = dy,
        )
    }

    override var pressA: () -> Unit = {
        //todo Aを押した時の処理を実装
    }

    companion object {
        const val MOVE_BORDER = 0.3f
        const val VIRTUAL_SCREEN_SIZE = 210
        const val VIRTUAL_PLAYER_SIZE = 20f
    }
}
