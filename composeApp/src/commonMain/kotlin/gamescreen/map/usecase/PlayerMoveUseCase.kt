package gamescreen.map.usecase

import gamescreen.map.domain.Player
import gamescreen.map.repository.player.PlayerPositionRepository

class PlayerMoveUseCase(
    private val playerPositionRepository: PlayerPositionRepository,
) {
    suspend operator fun invoke(
        player: Player,
    ) {
        playerPositionRepository.setPlayerPosition(
            player = player.move(),
        )
    }
}
