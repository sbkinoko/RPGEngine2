package gamescreen.map.usecase.event.actionevent

import values.EventType

interface ActionEventUseCase {
    operator fun invoke(
        eventType: EventType,
    )
}
