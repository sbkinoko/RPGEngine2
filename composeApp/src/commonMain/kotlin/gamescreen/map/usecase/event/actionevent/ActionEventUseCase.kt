package gamescreen.map.usecase.event.actionevent

import gamescreen.map.domain.background.BackgroundData
import values.event.EventType

interface ActionEventUseCase {
    operator fun invoke(
        eventType: EventType,
        backgroundData: BackgroundData,
    )
}
