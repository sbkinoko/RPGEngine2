package gamescreen.map.domain.collision.square

import gamescreen.map.domain.Point
import gamescreen.map.domain.collision.ShapeCollisionDetect
import gamescreen.map.domain.move

data class NormalSquare(
    val point: Point = Point(),
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

    override val x: Float
        get() = point.x

    override val y: Float
        get() = point.y

    override val leftSide: Float
        get() = point.x

    override val rightSide: Float
        get() = point.x + size

    override val topSide: Float
        get() = point.y

    override val bottomSide: Float
        get() = point.y + size

    override fun move(
        dx: Float,
        dy: Float,
    ): Square {
        return copy(
            point = point.move(
                dx = dx,
                dy = dy,
            )
        )
    }

    override fun moveTo(
        x: Float,
        y: Float,
    ): Square {
        return copy(
            point = Point(
                x = x,
                y = y,
            )
        )
    }
}