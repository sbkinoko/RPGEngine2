package gamescreen.map.usecase.settalk

import data.event.battle.BattleEventKey
import data.event.battle.BattleEventManager1
import data.event.talk.TalkEventKey1
import data.event.talk.TalkEventManager1
import data.event.talk.TalkEventManager2
import values.event.TalkID

class SetTalkUseCaseImpl(
    private val battleEventManager1: BattleEventManager1,

    private val talkEventManager1: TalkEventManager1,
    private val talkEventManager2: TalkEventManager2,
) : SetTalkUseCase {
    override fun invoke(talkEvent: TalkID) {

        when (talkEvent) {
            TalkID.Talk1 -> talkEventManager1.callEvent(TalkEventKey1.Talk)

            TalkID.Talk2 -> battleEventManager1.callEvent(BattleEventKey.Start)

            TalkID.Talk3 -> talkEventManager2.callEvent(
                TalkEventKey1.Talk
            )
        }
    }
}
