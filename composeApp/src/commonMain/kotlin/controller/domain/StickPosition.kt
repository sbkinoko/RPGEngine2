package controller.domain

import androidx.compose.ui.geometry.Offset
import common.Normalizer
import map.domain.Point

class StickPosition(
    val circleRadius: Int,
    stickSize: Int,
    position: Offset = Offset(
        x = circleRadius.toFloat(),
        y = circleRadius.toFloat(),
    ),
) {
    val isReleased: Boolean
        get() = tap.x == 0f && tap.y == 0f
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

    fun toCommand(): ArrowCommand {
        if (0.5 <= ratioX) {
            return ArrowCommand.Right
        }
        if (ratioX <= -0.5) {
            return ArrowCommand.Left
        }
        if (0.5 <= ratioY) {
            return ArrowCommand.Down
        }

        if (ratioY <= -0.5) {
            return ArrowCommand.Up
        }

        return ArrowCommand.None
    }
}
