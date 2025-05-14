package gamescreen.map.usecase.collision.geteventtype

import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.collision.square.Rectangle
import values.event.EventType

interface GetEventTypeUseCase {
    operator fun invoke(
        rectangle: Rectangle,
        backgroundData: BackgroundData,
    ): EventType
}
