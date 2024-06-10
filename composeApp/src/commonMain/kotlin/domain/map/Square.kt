package domain.map

open class Square(
    val displayPoint: Point = Point(),
    val size: Float,
) {
    constructor(
        x: Float,
        y: Float,
        size: Float
    ) : this(
        displayPoint = Point(
            x = x,
            y = y,
        ),
        size = size,
    )

    val x: Float
        get() = displayPoint.x

    val y: Float
        get() = displayPoint.y

    val leftSide: Float
        get() = displayPoint.x

    val rightSide: Float
        get() = displayPoint.x + size

    val topSide: Float
        get() = displayPoint.y

    val bottomSide: Float
        get() = displayPoint.y + size

    fun move(
        dx: Float = 0f,
        dy: Float = 0f,
    ) {
        displayPoint.move(
            dx = dx,
            dy = dy,
        )
    }

    fun moveTo(
        x: Float,
        y: Float,
    ) {
        displayPoint.x = x
        displayPoint.y = y
    }

    fun getNew(): Square {
        return Square(
            x = x,
            y = y,
            size = size,
        )
    }

    fun isLeft(other: Square): Boolean {
        return this.rightSide <= other.leftSide
    }

    fun isRight(other: Square): Boolean {
        return other.rightSide <= this.leftSide
    }

    fun isDown(other: Square): Boolean {
        return other.bottomSide <= this.topSide
    }

    fun isUp(other: Square): Boolean {
        return this.bottomSide <= other.topSide
    }

    /**
     * otherにこのsquareが包含されているかをチェックする
     * @param other 比較したいsquare
     */
    fun isIn(other: Square): Boolean {
        if (this.leftSide < other.leftSide)
            return false

        if (other.rightSide < this.rightSide)
            return false

        if (this.topSide < other.topSide)
            return false

        if (other.bottomSide < this.bottomSide)
            return false

        return true
    }
}
