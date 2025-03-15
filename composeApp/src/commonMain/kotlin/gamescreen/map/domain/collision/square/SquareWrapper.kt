package gamescreen.map.domain.collision.square

import gamescreen.map.domain.ObjectHeight

abstract class SquareWrapper<T : Rectangle>(
    open val square: Rectangle,
) : Rectangle {
    // 正方形なので高さでも幅でも一緒
    val size: Float
        get() = square.height

    override val height: Float
        get() = square.height

    override val width
        get() = square.width

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

    override val objectHeight: ObjectHeight
        get() = square.objectHeight

    abstract override fun move(dx: Float, dy: Float): T

    abstract override fun moveTo(x: Float, y: Float): T
}
