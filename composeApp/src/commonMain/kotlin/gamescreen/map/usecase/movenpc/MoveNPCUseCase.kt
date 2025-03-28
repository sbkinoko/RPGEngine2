package gamescreen.map.usecase.movenpc

import gamescreen.map.domain.Velocity
import gamescreen.map.domain.npc.NPCData

interface MoveNPCUseCase {

    /**
     * NPCを移動する
     */
    operator fun invoke(
        velocity: Velocity,
        npcData: NPCData,
    ): NPCData
}
