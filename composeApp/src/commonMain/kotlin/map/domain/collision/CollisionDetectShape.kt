package map.domain.collision

import androidx.compose.ui.graphics.Path

interface CollisionDetectShape {
    fun isOverlap(other: Square): Boolean

    fun toPath(screenRatio: Float): Path
}
