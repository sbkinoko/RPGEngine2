package layout.map

import domain.map.Player
import domain.map.Point
import domain.map.Velocity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import manager.map.BackgroundCellManager

class MapViewModel {
    private var player: Player = Player()
    private val mutablePlayerPosition = MutableStateFlow(player.getPoint())
    val playerPosition = mutablePlayerPosition.asStateFlow()

    private var tapPoint: Point? = null

    private var mutableBackgroundCellManager: MutableStateFlow<BackgroundCellManager?> =
        MutableStateFlow(null)
    val backgroundCellManger = mutableBackgroundCellManager.asStateFlow()

    fun updatePosition() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(30L)
                updateVelocity()
                if (player.isMoving) {
                    player.move()
                    mutableBackgroundCellManager.value?.moveBackgroundCell(
                        dx = player.velocity.x,
                        dy = player.velocity.y,
                    )
                    mutablePlayerPosition.value = player.getPoint()
                }
            }
        }
    }

    fun setTapPoint(
        x: Float,
        y: Float,
    ) {
        tapPoint = Point(
            x = x,
            y = y,
        )
    }

    private fun updateVelocity() {
        if (tapPoint == null) {
            return
        }

        val dx = (tapPoint?.x ?: 0f) - (player.getPoint().x + player.size / 2)
        val dy = (tapPoint?.y ?: 0f) - (player.getPoint().y + player.size / 2)
        val velocity = Velocity(
            dx = dx,
            dy = dy,
        )

        player.updateVelocity(velocity = velocity)
    }

    fun resetVelocity() {
        val velocity = Velocity(
            dx = 0f,
            dy = 0f,
        )
        tapPoint = null

        player.updateVelocity(velocity = velocity)
    }

    fun getPlayerSize(): Float {
        return player.size
    }

    fun initBackgroundCellManager(
        screenWidth: Int
    ) {
        mutableBackgroundCellManager.value = BackgroundCellManager(
            cellNum = 5,
            sideLength = screenWidth,
        )
    }
}
