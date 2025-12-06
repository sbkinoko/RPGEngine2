package gamescreen.map.usecase.settalk

import data.event.BattleEventKey
import data.event.BattleEventManager1
import data.event.TalkEventManager1
import data.event.TalkEventManager2
import data.event.TalkJob
import values.event.EventGroup
import values.event.EventGroup1
import values.event.EventGroup2
import values.event.EventGroupBattle
import values.event.EventJob

class SetTalkUseCaseImpl(
    private val battleEventManager1: BattleEventManager1,

    private val talkEventManager1: TalkEventManager1,
    private val talkEventManager2: TalkEventManager2,

    private val talkJob: TalkJob,
) : SetTalkUseCase {

    override fun invoke(
        eventGroup: EventGroup,
    ) {

        when (eventGroup) {
            is EventGroup1 -> talkEventManager1.callEvent(eventGroup)

            is EventGroupBattle -> battleEventManager1.callEvent(BattleEventKey.Start)

            is EventGroup2 -> talkEventManager2.callEvent(
                eventGroup
            )

            is EventJob -> talkJob.callEvent(
                key = eventGroup,
            )
        }
    }
}
