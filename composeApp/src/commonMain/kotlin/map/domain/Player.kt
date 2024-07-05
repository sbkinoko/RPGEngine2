package map.domain

import map.domain.collision.Square

class Player(
    val size: Float,
) {
    var square: Square = Square(
        size = size,
    )

    private var velocity = Velocity(
        x = 0f,
        y = 0f,
    )

    val maxVelocity: Float
        get() = velocity.maxVelocity

    fun move() {
        square.move(
            velocity.x,
            velocity.y,
        )
    }

    fun updateVelocity(
        velocity: Velocity
    ) {
        this.velocity = velocity
    }

    private fun moveTo(
        x: Float,
        y: Float,
    ) {
        square.moveTo(
            x = x - size / 2,
            y = y - size / 2
        )
    }

    fun moveTo(
        point: Point
    ) {
        moveTo(
            x = point.x,
            y = point.y,
        )
    }
}
