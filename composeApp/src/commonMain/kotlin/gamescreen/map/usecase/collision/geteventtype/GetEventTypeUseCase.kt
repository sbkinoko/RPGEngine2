package gamescreen.map.usecase.collision.geteventtype

import gamescreen.map.domain.collision.square.Square
import values.event.EventType

interface GetEventTypeUseCase {
    operator fun invoke(
        square: Square,
    ): EventType
}
