package gamescreen.map.domain.collision.square

import androidx.compose.ui.graphics.Path
import gamescreen.map.domain.Line
import gamescreen.map.domain.Point
import gamescreen.map.domain.collision.ShapeCollisionDetect

interface Rectangle : ShapeCollisionDetect {
    val width: Float
    val height: Float

    val baseX: Float
        get() = leftSide

    val baseY: Float
        get() = topSide

    val x: Float

    val y: Float

    val leftSide: Float

    val rightSide: Float

    val topSide: Float

    val bottomSide: Float

    private val leftTop
        get() = Point(
            x = leftSide,
            y = topSide,
        )

    private val leftBottom
        get() = Point(
            x = leftSide,
            y = bottomSide,
        )

    private val rightTop
        get() = Point(
            x = rightSide,
            y = topSide,
        )

    private val rightBottom
        get() = Point(
            x = rightSide,
            y = bottomSide,
        )

    override val lines: List<Line>
        get() = listOf(
            Line(leftTop, leftBottom),
            Line(leftBottom, rightBottom),
            Line(rightBottom, rightTop),
            Line(rightTop, leftTop),
            Line(leftTop, rightBottom),
            Line(rightTop, leftBottom)
        )


    override fun getPath(
        screenRatio: Float,
    ): Path {
        val path = Path()
        val left = x * screenRatio
        val top = y * screenRatio
        path.moveTo(
            left,
            top,
        )
        path.lineTo(left, top + height * screenRatio)
        path.lineTo(left + width * screenRatio, top + height * screenRatio)
        path.lineTo(left + width * screenRatio, top)

        path.lineTo(left, top)
        path.lineTo(left + width * screenRatio, top + height * screenRatio)

        // 右上
        path.moveTo(left + width * screenRatio, top)
        // 左下
        path.lineTo(left, top + height * screenRatio)

        path.close()
        return path
    }

    fun move(
        dx: Float = 0f,
        dy: Float = 0f,
    ): Rectangle

    fun moveTo(
        x: Float = 0f,
        y: Float = 0f,
    ): Rectangle

    fun isLeft(other: Rectangle): Boolean {
        return this.rightSide <= other.leftSide
    }

    fun isRight(other: Rectangle): Boolean {
        return other.rightSide <= this.leftSide
    }

    fun isDown(other: Rectangle): Boolean {
        return other.bottomSide <= this.topSide
    }

    fun isUp(other: Rectangle): Boolean {
        return this.bottomSide <= other.topSide
    }

    /**
     * otherにこのsquareが包含されているかをチェックする
     * @param other 比較したいsquare
     */
    fun isIn(other: Rectangle): Boolean {
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
