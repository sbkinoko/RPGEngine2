package gamescreen.map.usecase.collision.geteventtype

import gamescreen.map.domain.collision.square.NormalSquare
import values.EventType

interface GetEventTypeUseCase {
    operator fun invoke(
        square: NormalSquare,
    ): EventType
}
