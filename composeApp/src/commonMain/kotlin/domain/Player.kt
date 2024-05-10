package domain

class Player {
    private var point = Point()

    fun getPoint(): Point {
        return point
    }

    private var velocity = Velocity(
        dx = 1f,
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
