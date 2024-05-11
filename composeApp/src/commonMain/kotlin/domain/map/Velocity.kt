package domain.map

import kotlin.math.sqrt

class Velocity() {
    var x: Float = 0f
    var y: Float = 0f

    constructor(
        dx: Float,
        dy: Float,
        maxVelocity: Float = 10f,
    ) : this() {
        val dz = sqrt(dx * dx + dy *dy)
        val ratio = if (maxVelocity < dz) {
            maxVelocity / dz
        } else {
            1f
        }
        x = dx * ratio
        y = dy * ratio
    }
}
