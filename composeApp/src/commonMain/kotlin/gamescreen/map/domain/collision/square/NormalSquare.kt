package gamescreen.map.domain.collision.square

import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Point
import gamescreen.map.domain.collision.ShapeCollisionDetect
import gamescreen.map.domain.move

//fixme デフォルト値削除
//fixme このコンストラクタを利用している物は修正
data class NormalSquare(
    val point: Point = Point(),
    override val size: Float,
    override val objectHeight: ObjectHeight = ObjectHeight.None,
) : ShapeCollisionDetect,
    Square {

    constructor(
        x: Float,
        y: Float,
        size: Float,
        objectHeight: ObjectHeight = ObjectHeight.None,
    ) : this(
        point = Point(
            x = x,
            y = y,
        ),
        size = size,
        objectHeight = objectHeight,
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
