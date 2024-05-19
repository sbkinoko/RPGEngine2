package domain.map

class Player(
    val size: Float,
) {
    var square: Square

    init {
        square = Square(
            displayPoint = Point(
                x = 0f,
                y = 0f,
            ),
            size = size,
        )
    }

    val isMoving: Boolean
        get() = velocity.x != 0f ||
                velocity.y != 0f

    var velocity = Velocity(
        dx = 0f,
        dy = 0f,
    )

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
}
