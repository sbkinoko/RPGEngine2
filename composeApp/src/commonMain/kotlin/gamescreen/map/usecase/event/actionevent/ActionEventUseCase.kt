package gamescreen.map.usecase.event.actionevent

import values.event.EventType

interface ActionEventUseCase {
    operator fun invoke(
        eventType: EventType,
    )
}
