package gamescreen.map.usecase.collision.geteventtype

import gamescreen.map.domain.background.BackgroundData
import gamescreen.map.domain.collision.square.Rectangle
import gamescreen.map.domain.npc.NPCData
import values.event.EventType

interface GetEventTypeUseCase {
    // todo playerを受け取ってplayerを返すようにする
    operator fun invoke(
        rectangle: Rectangle,
        backgroundData: BackgroundData,
        npcData: NPCData,
    ): EventType
}
