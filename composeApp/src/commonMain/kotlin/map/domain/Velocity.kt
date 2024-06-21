package map.domain

import common.Normalizer

class Velocity(
    val maxVelocity: Float = MAX,
) {
    var x: Float = 0f
    var y: Float = 0f

    val isMoving: Boolean
        get() = x != 0f ||
                y != 0f

    constructor(
        dx: Float,
        dy: Float,
        maxVelocity: Float = MAX,
    ) : this(
        maxVelocity = maxVelocity,
    ) {
        Normalizer.normalize(
            x = dx,
            y = dy,
            max = maxVelocity,
        ).apply {
            this@Velocity.x = x
            this@Velocity.y = y
        }
    }

    companion object {
        const val MAX = 5f
    }
}
