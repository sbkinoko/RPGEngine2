package gamescreen.map.domain.collision

import gamescreen.map.domain.Point
import values.EventType

class EventSquare(
    override val eventID: EventType,
    point: Point = Point(),
    size: Float,
) : EventObject,
    Square(
        point = point,
        size = size,
    ) {
    constructor(
        eventID: EventType,
        x: Float,
        y: Float,
        size: Float,
    ) : this(
        eventID = eventID,
        point = Point(
            x = x,
            y = y,
        ),
        size = size,
    )
}
