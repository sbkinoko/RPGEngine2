package common

import gamescreen.map.domain.DisplayPoint
import kotlin.math.sqrt

class Normalizer {
    companion object {
        fun normalize(
            x: Float,
            y: Float,
            max: Number,
        ): DisplayPoint {
            val dz = sqrt(x * x + y * y)
            val fMax = max.toFloat()

            // maxを超える場合はmaxになるように調整
            val ratio = if (fMax < dz) {
                fMax / dz
            } else {
                1f
            }

            return DisplayPoint(
                x = x * ratio,
                y = y * ratio,
            )
        }
    }
}
