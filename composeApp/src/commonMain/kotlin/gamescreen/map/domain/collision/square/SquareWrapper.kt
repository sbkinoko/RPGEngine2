package gamescreen.map.domain.collision.square

import gamescreen.map.domain.Point

abstract class SquareWrapper(
    val normalSquare: NormalSquare,
) : Square {
    override val point: Point
        get() = normalSquare.point
    override val size: Float
        get() = normalSquare.size
}
