package gamescreen.map.domain.collision.square

import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Point
import gamescreen.map.domain.collision.ShapeCollisionDetect
import gamescreen.map.domain.move

data class NormalSquare(
    val point: Point,
    override val width: Float,
    override val height: Float,
    override val objectHeight: ObjectHeight,
) : ShapeCollisionDetect,
    Rectangle {

    constructor(
        x: Float = 0f,
        y: Float = 0f,
        size: Float,
        objectHeight: ObjectHeight = ObjectHeight.None,
    ) : this(
        point = Point(
            x = x,
            y = y,
        ),
        width = size,
        height = size,
        objectHeight = objectHeight,
    )

    constructor(
        x: Float = 0f,
        y: Float = 0f,
        width: Float,
        height: Float,
        objectHeight: ObjectHeight = ObjectHeight.None,
    ) : this(
        point = Point(
            x = x,
            y = y,
        ),
        width = width,
        height = height,
        objectHeight = objectHeight,
    )

    override val x: Float
        get() = point.x

    override val y: Float
        get() = point.y

    override val leftSide: Float
        get() = point.x

    override val rightSide: Float
        get() = point.x + width

    override val topSide: Float
        get() = point.y

    override val bottomSide: Float
        get() = point.y + height

    override fun move(
        dx: Float,
        dy: Float,
    ): Rectangle {
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
    ): Rectangle {
        return copy(
            point = Point(
                x = x,
                y = y,
            )
        )
    }

    companion object {
        fun smallSquare(
            size: Int,
            rate: Float,
        ): NormalSquare {
            return NormalSquare(
                x = size * rate,
                y = size * rate,
                size = size * (1 - 2 * rate),
            )
        }
    }
}
