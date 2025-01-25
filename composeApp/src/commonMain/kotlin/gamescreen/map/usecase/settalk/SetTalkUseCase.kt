package gamescreen.map.usecase.settalk

import values.event.TalkId

interface SetTalkUseCase {
    operator fun invoke(
        talkId: TalkId,
    )
}
