package domain.map

import common.Normalizer

class Velocity(
    val maxVelocity: Float = 20f,
) {
    var x: Float = 0f
    var y: Float = 0f

    constructor(
        dx: Float,
        dy: Float,
        maxVelocity: Float = 20f,
    ) : this(
        maxVelocity = maxVelocity,
    ) {
        val ratio = Normalizer.normalize(
            x = dx,
            y = dy,
            max = maxVelocity,
        )
        x = dx * ratio
        y = dy * ratio
    }
}
