package gamescreen.map.domain

import common.Normalizer

data class Velocity(
    val maxVelocity: Float = MAX,
    var x: Float = 0f,
    var y: Float = 0f,
) {
    init {
        Normalizer.normalize(
            x = x,
            y = y,
            max = maxVelocity,
        ).apply {
            this@Velocity.x = x
            this@Velocity.y = y
        }
    }

    val isMoving: Boolean
        get() = x != 0f ||
                y != 0f

    companion object {
        const val MAX = 5f
    }
}

fun Velocity.toDir(): PlayerDir {
    throw NotImplementedError()
}
