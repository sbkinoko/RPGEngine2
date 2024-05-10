package domain

import kotlin.math.sqrt

class Velocity() {
    var x: Float = 0f
    var y: Float = 0f

    private val maxVelocity = 10f

    constructor(
        dx: Float,
        dy: Float,
    ) : this() {
        val dz = sqrt(x * x + y * y)
        val ratio = if (maxVelocity < dz) {
            maxVelocity / dz
        } else {
            1f
        }
        x = dx * ratio
        y = dy * ratio
    }
}
