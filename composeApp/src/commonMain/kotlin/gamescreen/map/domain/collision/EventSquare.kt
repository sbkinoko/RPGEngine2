package gamescreen.map.domain.collision

import gamescreen.map.domain.Point
import values.EventType

class EventSquare(
    override val eventID: EventType,
    displayPoint: Point = Point(),
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
        displayPoint = Point(
            x = x,
            y = y,
        ),
        size = size,
    )
}
