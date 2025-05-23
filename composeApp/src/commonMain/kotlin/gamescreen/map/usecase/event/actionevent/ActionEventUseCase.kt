package gamescreen.map.usecase.event.actionevent

import gamescreen.map.domain.MapUiState
import values.event.EventType

interface ActionEventUseCase {
    operator fun invoke(
        eventType: EventType,
        mapUiState: MapUiState,
        update: (MapUiState) -> Unit,
    )
}
