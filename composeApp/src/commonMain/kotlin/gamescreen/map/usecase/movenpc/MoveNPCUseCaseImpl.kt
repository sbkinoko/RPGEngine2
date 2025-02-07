package gamescreen.map.usecase.movenpc

import gamescreen.map.domain.Velocity
import gamescreen.map.repository.npc.NPCRepository

class MoveNPCUseCaseImpl(
    private val npcRepository: NPCRepository,
) : MoveNPCUseCase {

    override operator fun invoke(
        velocity: Velocity,
    ) {
        val npcList = npcRepository.npcStateFlow.value.map { npc ->
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

        npcRepository.setNpc(
            npcList = npcList,
        )
    }
}
