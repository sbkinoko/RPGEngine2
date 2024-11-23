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

/**
 * 速度から向きを返す
 */
fun Velocity.toDir(): PlayerDir {
    if (!isMoving) {
        return PlayerDir.NONE
    }

    return if (x > y) {
        if (x > 0) {
            PlayerDir.RIGHT
        } else {
            PlayerDir.LEFT
        }
    } else {
        if (y > 0) {
            PlayerDir.DOWN
        } else {
            PlayerDir.UP
        }
    }
}
