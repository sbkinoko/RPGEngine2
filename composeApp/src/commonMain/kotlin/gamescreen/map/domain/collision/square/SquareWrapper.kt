package gamescreen.map.domain.collision.square

abstract class SquareWrapper(
    open val square: Square,
) : Square {
    override val size: Float
        get() = square.size

    override val x: Float
        get() = square.x

    override val y: Float
        get() = square.y

    override val leftSide: Float
        get() = square.leftSide

    override val rightSide: Float
        get() = square.rightSide

    override val topSide: Float
        get() = square.topSide

    override val bottomSide: Float
        get() = square.bottomSide

    override fun move(dx: Float, dy: Float): Square {
        return square.move(
            dx = dx,
            dy = dy,
        )
    }

    override fun moveTo(x: Float, y: Float): Square {
        return square.moveTo(
            x = x,
            y = y,
        )
    }
}
