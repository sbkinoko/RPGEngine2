package gamescreen.map.domain.collision.square

import gamescreen.map.domain.Point

abstract class SquareWrapper(
    open val square: Square,
) : Square {
    override val point: Point
        get() = square.point
    override val size: Float
        get() = square.size

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
