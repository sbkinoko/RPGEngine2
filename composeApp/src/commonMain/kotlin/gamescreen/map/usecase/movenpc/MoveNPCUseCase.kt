package gamescreen.map.usecase.movenpc

import gamescreen.map.domain.Velocity
import gamescreen.map.domain.npc.NPC

interface MoveNPCUseCase {

    /**
     * NPCを移動する
     */
    operator fun invoke(
        velocity: Velocity,
        npcList: List<NPC>
    ): List<NPC>
}
