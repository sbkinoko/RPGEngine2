package map.usecase

import map.repository.player.PlayerRepository

class PlayerMoveUseCase(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(
        vx: Float,
        vy: Float,
    ) {
        val square = playerRepository.getPlayerPosition().getNew()
        square.move(
            dx = vx,
            dy = vy,
        )
        playerRepository.setPlayerPosition(
            square = square,
        )
    }
}
