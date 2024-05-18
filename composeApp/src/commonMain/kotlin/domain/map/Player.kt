package domain.map

class Player {
    private var point = Point()

    val isMoving: Boolean
        get() = velocity.x != 0f ||
                velocity.y != 0f

    fun getPoint(): Point {
        return point
    }

    var velocity = Velocity(
        dx = 0f,
        dy = 0f,
    )

    var size: Float = 100f

    fun move() {
        point = Point(
            x = point.x + velocity.x,
            y = point.y + velocity.y,
        )
    }

    fun updateVelocity(
        velocity: Velocity
    ) {
        this.velocity = velocity
    }
}
