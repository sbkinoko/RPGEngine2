package gamescreen.map.usecase

import gamescreen.map.domain.Player
import gamescreen.map.repository.player.PlayerPositionRepository

class PlayerMoveUseCase(
    private val playerPositionRepository: PlayerPositionRepository
) {
    suspend operator fun invoke(
        player: Player,
    ) {
        val square = playerPositionRepository.getPlayerPosition().getNew()
        square.move(
            dx = player.velocity.x,
            dy = player.velocity.y,
        )
        playerPositionRepository.setPlayerPosition(
            square = square,
        )
    }
}
