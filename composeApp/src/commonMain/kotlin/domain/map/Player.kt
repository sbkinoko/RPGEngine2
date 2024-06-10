package domain.map

class Player(
    val size: Float,
) {
    var square: Square

    private var velocity = Velocity(
        dx = 0f,
        dy = 0f,
    )

    val maxVelocity: Float
        get() = velocity.maxVelocity

    init {
        square = Square(
            size = size,
        )
    }

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

    fun moveTo(
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
