package gamescreen.map.domain.collision.square

import androidx.compose.ui.graphics.Path
import gamescreen.map.domain.collision.ShapeCollisionDetect

interface Square : ShapeCollisionDetect {
    val size: Float

    fun move(
        dx: Float = 0f,
        dy: Float = 0f,
    ): Square

    fun moveTo(
        x: Float = 0f,
        y: Float = 0f,
    ): Square

    override val baseX: Float
        get() = leftSide

    override val baseY: Float
        get() = topSide

    val x: Float

    val y: Float

    val leftSide: Float

    val rightSide: Float

    val topSide: Float

    val bottomSide: Float

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

    override fun getPath(
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
