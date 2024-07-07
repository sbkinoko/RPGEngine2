package map.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import map.domain.collision.Square
import map.repository.player.PlayerRepository
import map.usecase.PlayerMoveUseCase

class Player(
    val size: Float,
    private val playerRepository: PlayerRepository,
    private val playerMoveUseCase: PlayerMoveUseCase,
) {
    var square: Square = Square(
        size = size,
    )

    private var velocity = Velocity(
        x = 0f,
        y = 0f,
    )

    val maxVelocity: Float
        get() = velocity.maxVelocity

    init {
        CoroutineScope(Dispatchers.Default).launch {
            playerRepository.setPlayerPosition(
                square = square,
            )
        }
    }

    fun move() {
        CoroutineScope(Dispatchers.Default).launch {
            playerMoveUseCase(
                vx = velocity.x,
                vy = velocity.y,
            )
            square = playerRepository.getPlayerPosition()
        }
    }

    fun updateVelocity(
        velocity: Velocity
    ) {
        this.velocity = velocity
    }

    private fun moveTo(
        x: Float,
        y: Float,
    ) {
        square.moveTo(
            x = x - size / 2,
            y = y - size / 2
        )
    }

    fun moveTo(
        point: Point
    ) {
        moveTo(
            x = point.x,
            y = point.y,
        )
    }
}
