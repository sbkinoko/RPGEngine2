package gamescreen.map.domain.collision.square

import gamescreen.map.domain.collision.EventObject
import values.EventType

data class EventSquare(
    override val eventID: EventType,
    val square: NormalSquare,
) : SquareWrapper(
    normalSquare = square,
), EventObject {
    constructor(
        x: Float,
        y: Float,
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
}
