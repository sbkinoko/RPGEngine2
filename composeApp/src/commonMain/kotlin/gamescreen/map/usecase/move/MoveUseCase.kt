package gamescreen.map.usecase.move

import gamescreen.map.domain.Velocity
import gamescreen.map.domain.collision.square.NormalSquare

interface MoveUseCase {

    /**
     * 移動
     */
    suspend operator fun invoke(
        actualVelocity: Velocity,
        tentativeVelocity: Velocity,
        fieldSquare: NormalSquare,
        playerMoveArea: NormalSquare,
    )
}
