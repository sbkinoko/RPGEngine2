package gamescreen.map.domain

import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.domain.collision.square.Square
import gamescreen.map.viewmodel.MapViewModel.Companion.VIRTUAL_PLAYER_SIZE

// fixme 移動用の速度と方向判定用の速度を分ける
data class Player(
    val size: Float,
    val square: Square,
    val velocity: Velocity,
    val dir: PlayerDir,
) {
    constructor(size: Float) : this(
        size = size,
        square = NormalSquare(
            size = size,
        ),
        velocity = Velocity(
            0f,
            0f,
        ),
        dir = PlayerDir.NONE,
    )

    val maxVelocity: Float
        get() = velocity.maxVelocity

    fun move(): Player {
        return copy(
            square = square.move(
                dx = velocity.x,
                dy = velocity.y,
            )
        )
    }

    fun move(dx: Float, dy: Float): Player {
        return copy(
            square = square.move(
                dx = dx,
                dy = dy,
            )
        )
    }

    fun moveTo(x: Float, y: Float): Player {
        return copy(
            square = square.moveTo(
                x = x,
                y = y,
            )
        )
    }

    val eventSquare: Square
        get() {
            val square = NormalSquare(
                size = size,
                point = Point(
                    x = square.x,
                    y = square.y,
                ),
            )

            return when (dir) {
                PlayerDir.UP -> square.copy(
                    point = square.point.copy(
                        y = square.y - size / 2
                    )
                )

                PlayerDir.DOWN -> square.copy(
                    point = square.point.copy(
                        y = square.y + VIRTUAL_PLAYER_SIZE / 2,
                    )
                )

                PlayerDir.LEFT -> square.copy(
                    point = square.point.copy(
                        x = square.x - VIRTUAL_PLAYER_SIZE / 2,
                    )
                )

                PlayerDir.RIGHT -> square.copy(
                    point = square.point.copy(
                        x = square.x + VIRTUAL_PLAYER_SIZE / 2,
                    )
                )

                PlayerDir.NONE -> square
            }
        }
}
