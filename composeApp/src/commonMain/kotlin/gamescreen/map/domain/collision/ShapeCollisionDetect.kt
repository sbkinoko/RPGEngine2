package gamescreen.map.domain.collision

import androidx.compose.ui.graphics.Path
import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.collision.square.Square

interface ShapeCollisionDetect {
    val baseX: Float
    val baseY: Float

    val objectHeight: ObjectHeight

    /**
     * 別の四角形と重なっているかどうかをチェック
     */
    fun isOverlap(other: Square): Boolean

    /**
     * 当たり判定描画ようにpathにする
     */
    fun getPath(
        screenRatio: Float,
    ): Path
}
