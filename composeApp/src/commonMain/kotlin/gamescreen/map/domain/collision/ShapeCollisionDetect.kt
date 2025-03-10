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
        //　高さが同じなら衝突しない
        if (other.objectHeight == this.objectHeight &&
            // ただし、Noneの場合は判定する
            other.objectHeight != ObjectHeight.None &&
            objectHeight != ObjectHeight.None
        ) {
            return false
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
