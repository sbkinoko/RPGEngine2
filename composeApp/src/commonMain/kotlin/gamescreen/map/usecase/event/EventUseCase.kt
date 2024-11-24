package gamescreen.map.usecase.event

import values.EventType

interface EventUseCase {
    operator fun invoke(
        eventType: EventType,
    )
}
