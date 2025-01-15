package gamescreen.map.domain.collision.square

import gamescreen.map.domain.collision.EventObject
import values.EventType

data class EventSquare(
    override val eventID: EventType,
    override val square: Square,
) : SquareWrapper<EventSquare>(
    square = square,
), EventObject {
    constructor(
        x: Float = 0f,
        y: Float = 0f,
        size: Float,
        eventID: EventType,
    ) : this(
        eventID = eventID,
        square = NormalSquare(
            x = x,
            y = y,
            size = size,
        )
    )

    override fun move(dx: Float, dy: Float): EventSquare {
        return this.copy(
            square = square.move(
                dx = dx,
                dy = dy,
            )
        )
    }

    override fun moveTo(x: Float, y: Float): EventSquare {
        return this.copy(
            square = square.moveTo(
                x = x,
                y = y,
            )
        )
    }
}
