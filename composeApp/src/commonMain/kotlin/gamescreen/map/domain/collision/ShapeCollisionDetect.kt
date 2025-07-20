package gamescreen.map.domain.collision

import androidx.compose.ui.graphics.Path
import gamescreen.map.domain.Line
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.isCrossWith
import gamescreen.map.domain.move

interface ShapeCollisionDetect {

    val objectHeight: ObjectHeight

    val lines: List<Line>

    fun movedLines(
        baseX: Float,
        baseY: Float,
    ): List<Line> {
        return lines.map {
            Line(
                it.point1.move(
                    dx = baseX,
                    dy = baseY
                ),
                it.point2.move(
                    dx = baseX,
                    dy = baseY,
                )
            )
        }
    }

    /**
     * 別の四角形と重なっているかどうかをチェック
     */
    fun isOverlap(
        other: ShapeCollisionDetect,
        baseX: Float = 0f,
        baseY: Float = 0f,
    ): Boolean {
        val otherHeight = other.objectHeight
        val thisHeight = this.objectHeight

        if (otherHeight::class == thisHeight::class) {
            if (otherHeight.height != thisHeight.height) {
                return false
            }
        }

        if (otherHeight::class == ObjectHeight.Sky::class ||
            thisHeight::class == ObjectHeight.Sky::class) {
            return false
        }

        // fixme 四角形同士の場合は簡略化したい
        other.lines.forEach { otherLine ->
            movedLines(
                baseX,
                baseY,
            ).forEach { thisLine ->
                if (otherLine.isCrossWith(thisLine)) {
                    return true
                }
            }
        }

        return false
    }

    /**
     * 当たり判定描画ようにpathにする
     */
    fun getPath(
        screenRatio: Float,
    ): Path
}
