package gamescreen.map.domain.collision.square

import gamescreen.map.domain.Point
import gamescreen.map.domain.collision.ShapeCollisionDetect
import gamescreen.map.domain.move

data class NormalSquare(
    override val point: Point = Point(),
    override val size: Float,
) : ShapeCollisionDetect,
    Square {
    constructor(
        x: Float,
        y: Float,
        size: Float
    ) : this(
        point = Point(
            x = x,
            y = y,
        ),
        size = size,
    )

    fun move(
        dx: Float = 0f,
        dy: Float = 0f,
    ): NormalSquare {
        return copy(
            point = point.move(
                dx = dx,
                dy = dy,
            )
        )
    }

    fun moveTo(
        x: Float,
        y: Float,
    ): NormalSquare {
        return copy(
            point = Point(
                x = x,
                y = y,
            )
        )
    }
}
