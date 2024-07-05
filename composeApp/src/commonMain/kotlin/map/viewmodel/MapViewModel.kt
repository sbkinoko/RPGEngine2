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
import map.domain.Velocity
import map.domain.VelocityManager
import map.domain.collision.Square
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
            checkMove()
            if (!canMove) {
                return
            }

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

    private var canMove = true
    private fun checkMove() {
        val square = player.square.getNew()
        square.move(
            dx = tentativePlayerVelocity.x,
            dy = tentativePlayerVelocity.y
        )

        // このままの速度で動けるなら移動
        if (backgroundManger.value.isCollided(square).not()) {
            return
        }

        //　x方向だけの移動ができるかチェック
        val onlyMoveX = player.square.getNew()
        onlyMoveX.move(
            dx = tentativePlayerVelocity.x,
            dy = 0f,
        )
        var canMoveX =
            backgroundManger.value.isCollided(onlyMoveX).not()

        //　y方向だけの移動ができるかチェック
        val onlyMoveY = player.square.getNew()
        onlyMoveY.move(
            dx = 0f,
            dy = tentativePlayerVelocity.y,
        )
        var canMoveY =
            backgroundManger.value.isCollided(onlyMoveY).not()

        if (canMoveX && canMoveY) {
            if (tentativePlayerVelocity.y <= tentativePlayerVelocity.x) {
//                canMoveX = true
                canMoveY = false
            } else {
                canMoveX = false
//                canMoveY = true
            }
        }

        tentativePlayerVelocity = changeVelocity(
            tentativePlayerVelocity,
            canMoveX,
            canMoveY,
        )
    }

    private fun changeVelocity(
        velocity: Velocity,
        canMoveX: Boolean,
        canMoveY: Boolean,
    ): Velocity {
        if (canMoveX) {
            var vMin = if (velocity.y < 0) {
                velocity.y
            } else {
                0f
            }
            var vMax = if (velocity.y < 0) {
                0f
            } else {
                velocity.y
            }

            var vy = (vMin + vMax) / 2

            for (cnt: Int in 0..5) {
                val square = player.square.getNew()
                square.move(
                    velocity.x,
                    vy,
                )
                if (backgroundManger.value.isCollided(square)) {
                    if (0 <= velocity.y) {
                        vMax = vy
                    } else {
                        vMin = vy
                    }
                } else {
                    if (0 <= velocity.y) {
                        vMin = vy
                    } else {
                        vMax = vy
                    }
                }
                vy = (vMin + vMax) / 2
            }
            return if (0 <= velocity.y) {
                velocity.copy(
                    y = vMin,
                )
            } else {
                velocity.copy(
                    y = vMax,
                )
            }
        }

        return Velocity(
            x = 0f,
            y = 0f,
        )
    }

    companion object {
        const val MOVE_BORDER = 0.3f
        const val VIRTUAL_SCREEN_SIZE = 210
        const val VIRTUAL_PLAYER_SIZE = 20f
    }
}
