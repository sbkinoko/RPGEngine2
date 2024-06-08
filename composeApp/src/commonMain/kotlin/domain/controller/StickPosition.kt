package domain.controller

import androidx.compose.ui.geometry.Offset
import kotlin.math.sqrt

class StickPosition(
    val circleRadius: Int,
    stickSize: Int,
    position: Offset = Offset(
        x = circleRadius.toFloat(),
        y = circleRadius.toFloat(),
    ),
) {
    private var tapX: Int = 0
    private var tapY: Int = 0

    val x: Int
        get() = tapX
    val y: Int
        get() = tapY

    val ratioX: Float
        get() = tapX / circleRadius.toFloat()

    val ratioY: Float
        get() = tapY / circleRadius.toFloat()

    /**
     * タップ位置をもとにスティックの表示位置を計算する
     */
    init {
        val dx = position.x - circleRadius
        val dy = position.y - circleRadius

        val movableAreaSize = circleRadius - stickSize

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
