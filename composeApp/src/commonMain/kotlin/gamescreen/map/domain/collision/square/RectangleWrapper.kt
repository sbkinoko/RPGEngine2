package gamescreen.map.domain.collision.square

import gamescreen.map.domain.ObjectHeight

abstract class RectangleWrapper<T : Rectangle>(
    open val rectangle: Rectangle,
) : Rectangle {
    override val height: Float
        get() = rectangle.height

    override val width
        get() = rectangle.width

    override val x: Float
        get() = rectangle.x

    override val y: Float
        get() = rectangle.y

    override val leftSide: Float
        get() = rectangle.leftSide

    override val rightSide: Float
        get() = rectangle.rightSide

    override val topSide: Float
        get() = rectangle.topSide

    override val bottomSide: Float
        get() = rectangle.bottomSide

    override val objectHeight: ObjectHeight
        get() = rectangle.objectHeight

    abstract override fun move(dx: Float, dy: Float): T

    abstract override fun moveTo(x: Float, y: Float): T
}
