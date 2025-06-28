package gamescreen.map.domain

import androidx.compose.runtime.Stable
import gamescreen.map.domain.collision.square.NormalRectangle
import gamescreen.map.domain.collision.square.Rectangle
import gamescreen.map.viewmodel.MapViewModel.Companion.VIRTUAL_PLAYER_SIZE
import values.event.EventType

@Stable
data class Player(
    val size: Float,
    val square: Rectangle,
    val actualVelocity: Velocity,
    val tentativeVelocity: Velocity,
    val moveDistance: Float,
    val eventType: EventType,
) {
    constructor(size: Float) : this(
        size = size,
        square = NormalRectangle(
            x = 0f,
            y = 0f,
            size = size,
            objectHeight = ObjectHeight.Ground(1),
        ),
        actualVelocity = Velocity(
            x = 0f,
            y = 0f,
        ),
        tentativeVelocity = Velocity(),
        moveDistance = 0f,
        eventType = EventType.None,
    )

    val dir
        get() = tentativeVelocity.toDir()

    val maxVelocity: Float
        get() = actualVelocity.maxVelocity

    fun move(): Player {
        return copy(
            square = square.move(
                dx = actualVelocity.x,
                dy = actualVelocity.y,
            ),
            moveDistance = moveDistance + actualVelocity.scalar,
        )
    }

    fun moveTo(
        x: Float,
        y: Float,
    ): Player {
        return copy(
            square = square.moveTo(
                x = x,
                y = y,
            ),
            moveDistance = 0f,
        )
    }

    fun changeHeight(targetHeight: ObjectHeight): Player {
        return copy(
            square = (square as NormalRectangle).copy(
                objectHeight = targetHeight,
            )
        )
    }

    val eventSquare: Rectangle
        get() {
            val square = NormalRectangle(
                size = size,
                x = square.x,
                y = square.y,
                objectHeight = square.objectHeight,
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
