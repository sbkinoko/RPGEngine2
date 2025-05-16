package gamescreen.map.usecase

import gamescreen.map.domain.Player

// fixme 消す
class PlayerMoveToUseCase {
    operator fun invoke(
        player: Player,
        x: Float,
        y: Float,
    ): Player {
        val updated = player.moveTo(
            x = x,
            y = y,
        )

        return updated
    }
}
