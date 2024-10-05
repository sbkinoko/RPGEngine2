package gamescreen.map.usecase

import gamescreen.map.domain.Player
import gamescreen.map.repository.player.PlayerRepository

class PlayerMoveUseCase(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(
        player: Player,
    ) {
        val square = playerRepository.getPlayerPosition().getNew()
        square.move(
            dx = player.velocity.x,
            dy = player.velocity.y,
        )
        playerRepository.setPlayerPosition(
            square = square,
        )
    }
}
