package viewmodel

import data.map.mapdata.LoopMap
import data.map.mapdata.NonLoopMap
import domain.controller.ControllerCallback
import domain.map.BackgroundCell
import domain.map.Player
import domain.map.Point
import domain.map.Square
import domain.map.Velocity
import domain.map.VelocityManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import manager.map.BackgroundManager

class MapViewModel(
    playerSize: Float,
) : ControllerCallback {
    private var player: Player
    private val mutablePlayerPosition: MutableStateFlow<Square>
    val playerPosition: StateFlow<Square>

    private var tapPoint: Point? = null

    private var mutableBackgroundManager: MutableStateFlow<BackgroundManager?> =
        MutableStateFlow(null)
    val backgroundManger = mutableBackgroundManager.asStateFlow()

    private lateinit var playerMoveArea: Square

    private var backGroundVelocity: Velocity = Velocity()
    private var tentativePlayerVelocity: Velocity = Velocity()

    init {
        player = Player(
            initX = 700f,
            initY = 700f,
            size = playerSize,
        )
        mutablePlayerPosition = MutableStateFlow(player.square)
        playerPosition = mutablePlayerPosition.asStateFlow()
    }

    fun initBackgroundCellManager(
        screenWidth: Int,
    ) {
        mutableBackgroundManager.value = BackgroundManager(
            cellNum = 5,
            sideLength = screenWidth,
        )
        backgroundManger.value?.setMapData(LoopMap())

        playerMoveArea = Square(
            x = (MOVE_BORDER * screenWidth),
            y = (MOVE_BORDER * screenWidth),
            size = ((1 - 2 * MOVE_BORDER) * screenWidth),
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
            mutableBackgroundManager.value?.moveBackgroundCell(
                velocity = backGroundVelocity
            )
            backgroundManger.value?.apply {
                findCellIncludePlayer(
                    playerSquare = player.square
                )
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
        val vx = player.velocity.maxVelocity * dx
        val vy = player.velocity.maxVelocity * dy

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
        player.velocity = mediatedVelocity.first
        backGroundVelocity = mediatedVelocity.second
    }

    /**
     * プレイヤーを中心に移動する
     */
    private fun setPlayerCenter() {
        val center = backgroundManger.value?.getCenterOfDisplay() ?: return
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
                backgroundManger.value?.setMapData(NonLoopMap())
                reloadMapData(
                    mapX = 0,
                    mapY = 2,
                )
            }

            4 -> {
                backgroundManger.value?.setMapData(LoopMap())
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
        backgroundManger.value?.resetBackgroundCellPosition(
            mapX = mapX,
            mapY = mapY,
        )
        backgroundManger.value?.findCellIncludePlayer(
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

    companion object {
        private const val MOVE_BORDER = 0.3f
    }
}
