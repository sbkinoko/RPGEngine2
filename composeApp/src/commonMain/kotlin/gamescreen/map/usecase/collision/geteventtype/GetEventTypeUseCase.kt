package gamescreen.map.usecase.collision.geteventtype

import gamescreen.map.domain.collision.Square
import values.EventConstants

interface GetEventTypeUseCase {
    operator fun invoke(
        square: Square,
    ): EventConstants
}
