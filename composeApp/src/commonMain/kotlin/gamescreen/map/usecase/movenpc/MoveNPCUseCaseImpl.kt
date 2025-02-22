package gamescreen.map.usecase.movenpc

import gamescreen.map.domain.Velocity
import gamescreen.map.domain.npc.NPCData

class MoveNPCUseCaseImpl : MoveNPCUseCase {

    override operator fun invoke(
        velocity: Velocity,
        npcData: NPCData,
    ): NPCData {
        return npcData.map { npc ->
            npc.move(
                velocity = velocity,
            )
        }
    }
}
