package gamescreen.map.usecase.movenpc

import gamescreen.map.domain.Velocity

interface MoveNPCUseCase {

    /**
     * NPCを移動する
     */
    operator fun invoke(
        velocity: Velocity,
    )
}
