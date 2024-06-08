package domain.controller

import androidx.compose.ui.geometry.Offset
import common.Normalizer
import domain.map.Point

class StickPosition(
    val circleRadius: Int,
    stickSize: Int,
    position: Offset = Offset(
        x = circleRadius.toFloat(),
        y = circleRadius.toFloat(),
    ),
) {
    private var tap: Point

    val x: Int
        get() = tap.x.toInt()
    val y: Int
        get() = tap.y.toInt()

    val ratioX: Float
        get() = x / circleRadius.toFloat()

    val ratioY: Float
        get() = y / circleRadius.toFloat()

    /**
     * タップ位置をもとにスティックの表示位置を計算する
     */
    init {
        val dx = position.x - circleRadius
        val dy = position.y - circleRadius
        val movableAreaSize = circleRadius - stickSize

        tap = Normalizer.normalize(
            x = dx,
            y = dy,
            max = movableAreaSize,
        )
    }
}
