package domain.map

class Player(
    initX: Float = 0f,
    initY: Float = 0f,
    val size: Float,
) {
    var square: Square

    init {
        square = Square(
            displayPoint = Point(
                x = initX,
                y = initY,
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

    fun moveTo(
        x: Float,
        y: Float,
    ) {
        square.moveTo(
            x = x,
            y = y
        )
    }
}
