package domain.map

import kotlin.math.sqrt

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
        // todo 後でスティックと共通化する
        val dz = sqrt(dx * dx + dy * dy)
        val ratio = if (maxVelocity < dz) {
            maxVelocity / dz
        } else {
            1f
        }
        x = dx * ratio
        y = dy * ratio
    }
}
