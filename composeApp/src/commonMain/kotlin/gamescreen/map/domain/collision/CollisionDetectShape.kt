package gamescreen.map.domain.collision

import androidx.compose.ui.graphics.Path

interface CollisionDetectShape {

    val baseX: Float
    val baseY: Float

    fun isOverlap(other: Square): Boolean

    fun toPath(
        screenRatio: Float,
    ): Path
}
