package domain.map

data class Square(
    val displayPoint: Point = Point(),
    val size: Float,
) {

    constructor(
        x: Float,
        y: Float,
        size: Float
    ) : this(
        displayPoint = Point(
            x = x,
            y = y,
        ),
        size = size,
    )

    val x: Float
        get() = displayPoint.x

    val y: Float
        get() = displayPoint.y

    val leftSide: Float
        get() = displayPoint.x

    val rightSide: Float
        get() = displayPoint.x + size

    val topSide: Float
        get() = displayPoint.y

    val bottomSide: Float
        get() = displayPoint.y + size

    fun move(
        dx: Float = 0f,
        dy: Float = 0f,
    ) {
        displayPoint.move(
            dx = dx,
            dy = dy,
        )
    }

    fun getNew(): Square {
        return Square(
            x = x,
            y = y,
            size = size,
        )
    }
}
