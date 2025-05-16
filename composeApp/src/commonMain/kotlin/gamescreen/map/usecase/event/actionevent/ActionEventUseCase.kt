package gamescreen.map.usecase.event.actionevent

import gamescreen.map.domain.Player
import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.npc.NPCData
import values.event.EventType

interface ActionEventUseCase {
    operator fun invoke(
        eventType: EventType,
        backgroundData: BackgroundData,
        npcData: NPCData,
        player: Player,
    )
}
