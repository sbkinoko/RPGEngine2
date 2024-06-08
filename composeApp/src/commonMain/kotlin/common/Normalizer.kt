package common

import domain.map.Point
import kotlin.math.sqrt

class Normalizer {
    companion object {
        fun normalize(
            x: Float,
            y: Float,
            max: Number,
        ): Point {
            val dz = sqrt(x * x + y * y)
            val fMax = max.toFloat()

            // maxを超える場合はmaxになるように調整
            val ratio = if (fMax < dz) {
                fMax / dz
            } else {
                1f
            }

            return Point(
                x = x * ratio,
                y = y * ratio,
            )
        }
    }
}
