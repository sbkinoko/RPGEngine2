package gamescreen.map.usecase.settalk

import values.event.TalkID

interface SetTalkUseCase {
    operator fun invoke(
        talkEvent: TalkID,
    )
}
