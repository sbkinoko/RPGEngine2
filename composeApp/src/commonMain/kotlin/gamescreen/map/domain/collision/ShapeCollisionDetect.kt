package gamescreen.map.domain.collision

import androidx.compose.ui.graphics.Path
import gamescreen.map.domain.Line
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.isCrossWith

interface ShapeCollisionDetect {
    val baseX: Float
    val baseY: Float

    val objectHeight: ObjectHeight

    val lines: List<Line>

    /**
     * 別の四角形と重なっているかどうかをチェック
     */
    fun isOverlap(other: ShapeCollisionDetect): Boolean {
        val otherHeight = other.objectHeight
        val thisHeight = this.objectHeight

        if (otherHeight::class == thisHeight::class) {
            if (otherHeight.height != thisHeight.height) {
                return false
            }
        }

        // fixme 四角形同士の場合は簡略化したい
        other.lines.forEach { otherLine ->
            lines.forEach { thisLine ->
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
