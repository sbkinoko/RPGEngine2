package gamescreen.map.domain.collision.triangle

import androidx.compose.ui.graphics.Path
import common.extension.lineTo
import common.extension.moveTo
import gamescreen.map.domain.Line
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Point
import gamescreen.map.domain.collision.ShapeCollisionDetect
import gamescreen.map.domain.move

data class ConvexPolygon(
    override val baseX: Float,
    override val baseY: Float,
    override val objectHeight: ObjectHeight = ObjectHeight.None,
    val pointList: List<Point>,
) : ShapeCollisionDetect {
    private val actualPointList = pointList.map {
        it.move(baseX, baseY)
    }

    override val lines: List<Line> = actualPointList.run {
        val lineList = mutableListOf<Line>()

        actualPointList.map { point1 ->
            actualPointList.map inner@{ point2 ->
                if (point1 == point2) {
                    return@inner
                }

                lineList += Line(point1, point2)
            }
        }
        lineList
    }

    override fun getPath(screenRatio: Float): Path {
        val path = Path().also {
            for (index1: Int in 0..pointList.size) {
                for (index2: Int in index1 + 1..pointList.size) {
                    it.moveTo(pointList[index1], screenRatio)
                    it.lineTo(pointList[index2], screenRatio)
                }
            }

            it.close()
        }
        return path
    }
}
