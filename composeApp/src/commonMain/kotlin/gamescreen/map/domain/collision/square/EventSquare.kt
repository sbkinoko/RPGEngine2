package gamescreen.map.domain.collision.square

import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.collision.EventObject
import values.event.EventType

data class EventSquare(
    override val eventType: EventType,
    override val square: Square,
) : SquareWrapper<EventSquare>(
    square = square,
), EventObject {

    constructor(
        x: Float = 0f,
        y: Float = 0f,
        size: Float,
        eventType: EventType,
        objectHeight: ObjectHeight,
    ) : this(
        eventType = eventType,
        square = NormalSquare(
            x = x,
            y = y,
            size = size,
            objectHeight = objectHeight,
        ),
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
