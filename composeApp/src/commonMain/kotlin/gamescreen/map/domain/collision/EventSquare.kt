package gamescreen.map.domain.collision

import gamescreen.map.domain.DisplayPoint
import values.EventType

class EventSquare(
    override val eventID: EventType,
    displayPoint: DisplayPoint = DisplayPoint(),
    size: Float,
) : EventObject,
    Square(
        displayPoint = displayPoint,
        size = size,
    ) {
    constructor(
        eventID: EventType,
        x: Float,
        y: Float,
        size: Float,
    ) : this(
        eventID = eventID,
        displayPoint = DisplayPoint(
            x = x,
            y = y,
        ),
        size = size,
    )
}
