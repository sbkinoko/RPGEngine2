package gamescreen.map.domain.collision

import gamescreen.map.domain.Point
import values.EventConstants

class EventSquare(
    override val eventID: EventConstants,
    displayPoint: Point = Point(),
    size: Float,
) : EventObject, Square(
    displayPoint = displayPoint,
    size = size,
) {
    constructor(
        eventID: EventConstants,
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
