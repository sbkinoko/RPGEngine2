package gamescreen.map.domain.collision

import androidx.compose.ui.graphics.Path
import gamescreen.map.domain.Point

open class Square(
    open val point: Point = Point(),
    open val size: Float,
) : CollisionDetectShape {
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

    override val baseX: Float
        get() = leftSide

    override val baseY: Float
        get() = topSide

    val x: Float
        get() = point.x

    val y: Float
        get() = point.y

    val leftSide: Float
        get() = point.x

    val rightSide: Float
        get() = point.x + size

    val topSide: Float
        get() = point.y

    val bottomSide: Float
        get() = point.y + size

    fun move(
        dx: Float = 0f,
        dy: Float = 0f,
    ) {
        point.move(
            dx = dx,
            dy = dy,
        )
    }

    fun moveTo(
        x: Float,
        y: Float,
    ) {
        point.x = x
        point.y = y
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

    override fun isOverlap(other: Square): Boolean {
        if (this.rightSide < other.leftSide)
            return false

        if (other.rightSide < this.leftSide)
            return false

        if (this.bottomSide < other.topSide)
            return false

        if (other.bottomSide < this.topSide)
            return false

        return true
    }

    override fun toPath(
        screenRatio: Float,
    ): Path {
        val path = Path()
        val left = 0f
        val top = 0f
        path.moveTo(
            left,
            top,
        )
        path.lineTo(left, size * screenRatio)
        path.lineTo(size * screenRatio, size * screenRatio)
        path.lineTo(size * screenRatio, top)
        path.close()
        return path
    }
}
