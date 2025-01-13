package gamescreen.map.domain.collision.square

abstract class SquareWrapper<T : Square>(
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

    abstract override fun move(dx: Float, dy: Float): T

    abstract override fun moveTo(x: Float, y: Float): T
}
