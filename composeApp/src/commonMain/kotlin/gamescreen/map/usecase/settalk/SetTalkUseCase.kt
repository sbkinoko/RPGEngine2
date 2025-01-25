package gamescreen.map.usecase.settalk

import values.event.TalkEvent

interface SetTalkUseCase {
    operator fun invoke(
        talkEvent: TalkEvent,
    )
}
