package gamescreen.map.domain.collision.triangle

import androidx.compose.ui.graphics.Path
import common.extension.lineTo
import common.extension.moveTo
import gamescreen.map.domain.Line
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Point
import gamescreen.map.domain.collision.ShapeCollisionDetect
import gamescreen.map.domain.move

data class Triangle(
    override val baseX: Float,
    override val baseY: Float,
    val point1: Point,
    val point2: Point,
    val point3: Point,
    override val objectHeight: ObjectHeight = ObjectHeight.None,
) : ShapeCollisionDetect {
    private val actualPoint1 = point1.move(baseX, baseY)
    private val actualPoint2 = point2.move(baseX, baseY)
    private val actualPoint3 = point3.move(baseX, baseY)

    override val lines: List<Line>
        get() = listOf(
            Line(
                actualPoint1,
                actualPoint2,
            ),
            Line(
                actualPoint2,
                actualPoint3,
            ),
            Line(
                actualPoint3,
                actualPoint1,
            )
        )

    override fun getPath(screenRatio: Float): Path {
        val path = Path().also {

            it.moveTo(point1, screenRatio)
            it.lineTo(point2, screenRatio)
            it.lineTo(point3, screenRatio)

            it.close()
        }
        return path
    }
}
