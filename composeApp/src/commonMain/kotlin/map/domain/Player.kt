package map.domain

class Player(
    val size: Float,
) {
    var velocity = Velocity(
        x = 0f,
        y = 0f,
    )

    val maxVelocity: Float
        get() = velocity.maxVelocity

    fun updateVelocity(
        velocity: Velocity
    ) {
        this.velocity = velocity
    }
}
