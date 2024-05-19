package layout.map

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
import manager.map.BackgroundCellManager

class MapViewModel(
    playerSize: Float,
) {
    private var player: Player
    private val mutablePlayerPosition: MutableStateFlow<Square>
    val playerPosition: StateFlow<Square>

    private var tapPoint: Point? = null

    private var mutableBackgroundCellManager: MutableStateFlow<BackgroundCellManager?> =
        MutableStateFlow(null)
    val backgroundCellManger = mutableBackgroundCellManager.asStateFlow()

    init {
        player = Player(
            size = playerSize,
        )
        mutablePlayerPosition = MutableStateFlow(player.square)
        playerPosition = mutablePlayerPosition.asStateFlow()
    }

    fun initBackgroundCellManager(
        screenWidth: Int,
    ) {
        mutableBackgroundCellManager.value = BackgroundCellManager(
            cellNum = 5,
            sideLength = screenWidth,
        )
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
                    player.move()
                    // todo いい感じにする方法を探す
                    mutablePlayerPosition.value = player.square.getNew()
                    mutableBackgroundCellManager.value?.moveBackgroundCell(
                        dx = player.velocity.x,
                        dy = player.velocity.y,
                    )
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
}
