package gamescreen.map.usecase.movenpc

import gamescreen.map.domain.Velocity
import gamescreen.map.domain.npc.NPC

class MoveNPCUseCaseImpl : MoveNPCUseCase {

    override operator fun invoke(
        velocity: Velocity,
        npcList: List<NPC>
    ): List<NPC> {
        return npcList.map { npc ->
            npc.eventSquare.let {
                npc.copy(
                    eventSquare = it.copy(
                        square = it.square.move(
                            dx = velocity.x,
                            dy = velocity.y,
                        )
                    )
                )
            }
        }
    }
}
