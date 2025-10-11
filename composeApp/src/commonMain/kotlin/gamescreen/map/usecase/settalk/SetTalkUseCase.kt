package gamescreen.map.usecase.settalk

import values.event.EventGroup

interface SetTalkUseCase {
    operator fun invoke(
        eventGroup: EventGroup,
    )
}
