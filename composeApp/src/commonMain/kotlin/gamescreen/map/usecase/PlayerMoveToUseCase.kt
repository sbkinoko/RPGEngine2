package gamescreen.map.usecase

import gamescreen.map.repository.player.PlayerPositionRepository

class PlayerMoveToUseCase(
    private val playerPositionRepository: PlayerPositionRepository,
) {
    suspend operator fun invoke(
        x: Float,
        y: Float,
    ) {
        val player = playerPositionRepository
            .getPlayerPosition()
            .moveTo(
                x = x,
                y = y,
            )

        playerPositionRepository.setPlayerPosition(
            player = player,
        )
    }
}
