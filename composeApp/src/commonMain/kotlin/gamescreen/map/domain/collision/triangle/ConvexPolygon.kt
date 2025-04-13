package gamescreen.map.domain.collision.triangle

import androidx.compose.ui.graphics.Path
import common.extension.lineTo
import common.extension.moveTo
import gamescreen.map.domain.Line
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Point
import gamescreen.map.domain.collision.ShapeCollisionDetect

data class ConvexPolygon(
    override val objectHeight: ObjectHeight = ObjectHeight.None,
    val pointList: List<Point>,
) : ShapeCollisionDetect {

    constructor(
        objectHeight: ObjectHeight = ObjectHeight.None,
        vararg points: Point,
    ) : this(
        objectHeight = objectHeight,
        pointList = points.toList(),
    )

    override val lines: List<Line> = run {
        val lineList = mutableListOf<Line>()

        pointList.map { point1 ->
            pointList.map inner@{ point2 ->
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
            for (index1: Int in pointList.indices) {
                for (index2: Int in (index1 + 1) until pointList.size) {
                    it.moveTo(pointList[index1], screenRatio)
                    it.lineTo(pointList[index2], screenRatio)
                }
            }

            it.close()
        }
        return path
    }
}
