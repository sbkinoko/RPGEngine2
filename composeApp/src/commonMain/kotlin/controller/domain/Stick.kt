package controller.domain

import androidx.compose.ui.geometry.Offset
import common.Normalizer
import map.domain.Point

data class Stick(
    val areaRadius: Int,
    val stickRadius: Int,
    val position: Offset = Offset(
        x = areaRadius.toFloat(),
        y = areaRadius.toFloat(),
    ),
) {
    val isReleased: Boolean
        get() = tap.x == 0f && tap.y == 0f
    private var tap: Point

    val x: Int
        get() = tap.x.toInt()
    val y: Int
        get() = tap.y.toInt()

    private val ratioX: Float
        get() = x / areaRadius.toFloat()

    private val ratioY: Float
        get() = y / areaRadius.toFloat()

    val center: Offset
        get() = Offset(
            x = areaRadius.toFloat(),
            y = areaRadius.toFloat(),
        )

    /**
     * タップ位置をもとにスティックの表示位置を計算する
     */
    init {
        val dx = position.x - areaRadius
        val dy = position.y - areaRadius
        val movableAreaSize = areaRadius - stickRadius

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
