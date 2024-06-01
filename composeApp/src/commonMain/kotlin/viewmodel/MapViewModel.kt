package viewmodel

import data.map.mapdata.LoopMap
import data.map.mapdata.NonLoopMap
import domain.map.BackgroundCell
import domain.map.Player
import domain.map.Point
import domain.map.Square
import domain.map.Velocity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import manager.map.BackgroundManager

class MapViewModel(
    playerSize: Float,
) {
    private var player: Player
    private val mutablePlayerPosition: MutableStateFlow<Square>
    val playerPosition: StateFlow<Square>

    private var tapPoint: Point? = null

    private var mutableBackgroundManager: MutableStateFlow<BackgroundManager?> =
        MutableStateFlow(null)
    val backgroundManger = mutableBackgroundManager.asStateFlow()

    private lateinit var playerMoveArea: Square

    private var backGroundVelocity: Velocity = Velocity()

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
        backgroundManger.value?.mapData = LoopMap()

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
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(30L)
                updateVelocity()
                if (player.isMoving) {
                    mediateVelocity()
                    player.move()
                    // todo いい感じにする方法を探す
                    mutablePlayerPosition.value = player.square.getNew()
                    mutableBackgroundManager.value?.moveBackgroundCell(
                        velocity = backGroundVelocity
                    )
                    backgroundManger.value?.apply {
                        findCellIncludePlayer(
                            player = player
                        )
                        eventCell?.apply {
                            callCellEvent(this)
                        }
                    }
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
    private fun updateVelocity() {
        if (tapPoint == null) {
            return
        }

        val dx = (tapPoint?.x ?: 0f) - (player.square.x + player.size / 2)
        val dy = (tapPoint?.y ?: 0f) - (player.square.y + player.size / 2)
        val velocity = Velocity(
            dx = dx,
            dy = dy,
        )

        player.updateVelocity(velocity = velocity)
    }

    /**
     * 速度を0にする
     */
    fun resetVelocity() {
        val velocity = Velocity(
            dx = 0f,
            dy = 0f,
        )
        tapPoint = null

        player.updateVelocity(velocity = velocity)
    }

    /**
     * playerを動かすか、背景を動かすか決定する
     */
    fun mediateVelocity() {
        var vx = 0f
        var vy = 0f
        if ((player.square.isLeft(playerMoveArea) &&
                    player.velocity.x < 0) ||
            (player.square.isRight(playerMoveArea) &&
                    0 < player.velocity.x)
        ) {
            vx = -(player.velocity.x)
            player.velocity.x = 0f
        }

        if ((player.square.isUp(playerMoveArea) &&
                    player.velocity.y < 0) ||
            (player.square.isDown(playerMoveArea) &&
                    0 < player.velocity.y)
        ) {
            vy = -(player.velocity.y)
            player.velocity.y = 0f

        }

        backGroundVelocity = Velocity(
            dx = vx,
            dy = vy,
        )
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
                backgroundManger.value?.mapData = NonLoopMap()
                reloadMapData(
                    mapX = 0,
                    mapY = 2,
                )
            }

            4 -> {
                backgroundManger.value?.mapData = LoopMap()
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
            player = player
        )
    }

    companion object {
        private const val MOVE_BORDER = 0.3f
    }
}
