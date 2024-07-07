package map.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import map.domain.collision.Square
import map.repository.player.PlayerRepository
import map.usecase.PlayerMoveToUseCase
import map.usecase.PlayerMoveUseCase

class Player(
    val size: Float,
    private val playerRepository: PlayerRepository,
    private val playerMoveUseCase: PlayerMoveUseCase,
    private val playerMoveToUseCase: PlayerMoveToUseCase,
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

    fun moveTo(
        point: Point
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            playerMoveToUseCase(
                x = point.x - size / 2,
                y = point.y - size / 2,
            )
            square = playerRepository.getPlayerPosition()
        }
    }
}
