package gamescreen.map.usecase.settalk

import data.event.battle.BattleEventKey
import data.event.battle.BattleEventManager1
import data.event.talk.TalkEventKey1
import data.event.talk.TalkEventManager1
import values.event.TalkEvent

class SetTalkUseCaseImpl(
    private val battleEventManager1: BattleEventManager1,

    private val talkEventManager1: TalkEventManager1,
) : SetTalkUseCase {
    override fun invoke(talkEvent: TalkEvent) {

        when (talkEvent) {
            TalkEvent.Talk1 -> talkEventManager1.callEvent(TalkEventKey1.Talk)

            TalkEvent.Talk2 -> battleEventManager1.callEvent(BattleEventKey.Start)
        }
    }
}
