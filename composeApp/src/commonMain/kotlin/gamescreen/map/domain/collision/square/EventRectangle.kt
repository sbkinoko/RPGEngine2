package gamescreen.map.domain.collision.square

import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.collision.EventObject
import values.event.EventType

data class EventRectangle(
    override val eventType: EventType,
    override val rectangle: Rectangle,
) : RectangleWrapper<EventRectangle>(
    rectangle = rectangle,
), EventObject {

    constructor(
        x: Float = 0f,
        y: Float = 0f,
        size: Float,
        eventType: EventType,
        objectHeight: ObjectHeight,
    ) : this(
        eventType = eventType,
        rectangle = NormalRectangle(
            x = x,
            y = y,
            size = size,
            objectHeight = objectHeight,
        ),
    )

    constructor(
        x: Float = 0f,
        y: Float = 0f,
        width: Float,
        height: Float,
        eventType: EventType,
        objectHeight: ObjectHeight,
    ) : this(
        eventType = eventType,
        rectangle = NormalRectangle(
            x = x,
            y = y,
            width = width,
            height = height,
            objectHeight = objectHeight,
        ),
    )

    override fun move(
        dx: Float,
        dy: Float,
    ): EventRectangle {
        return this.copy(
            rectangle = rectangle.move(
                dx = dx,
                dy = dy,
            )
        )
    }

    override fun moveTo(
        x: Float,
        y: Float,
    ): EventRectangle {
        return this.copy(
            rectangle = rectangle.moveTo(
                x = x,
                y = y,
            )
        )
    }
}
