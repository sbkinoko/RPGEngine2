package gamescreen.map.usecase

import gamescreen.map.repository.player.PlayerRepository

class PlayerMoveToUseCase(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(
        x: Float,
        y: Float,
    ) {
        val square = playerRepository.getPlayerPosition().getNew()
        square.moveTo(
            x = x,
            y = y,
        )
        playerRepository.setPlayerPosition(
            square = square,
        )
    }
}
