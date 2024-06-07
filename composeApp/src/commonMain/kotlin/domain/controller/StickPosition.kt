package domain.controller

import androidx.compose.ui.geometry.Offset
import kotlin.math.sqrt

class StickPosition(
    circleSize: Int,
    stickSize: Int,
    position: Offset = Offset(
        x = circleSize.toFloat(),
        y = circleSize.toFloat(),
    ),
) {
    private var tapX: Int = 0
    private var tapY: Int = 0

    val x: Int
        get() = tapX
    val y: Int
        get() = tapY

    /**
     * タップ位置をもとにスティックの表示位置を計算する
     */
    init {
        val dx = position.x - circleSize
        val dy = position.y - circleSize

        val movableAreaSize = circleSize - stickSize

        val dz = sqrt(dx * dx + dy * dy)
        val ratio = if (movableAreaSize < dz) {
            movableAreaSize / dz
        } else {
            1f
        }
        tapX = (dx * ratio).toInt()
        tapY = (dy * ratio).toInt()
    }
}
