package gamescreen.map.usecase.moveplayer

import gamescreen.map.domain.Player
import gamescreen.map.repository.player.PlayerPositionRepository

class PlayerMoveUseCaseImpl(
    private val playerPositionRepository: PlayerPositionRepository,
) : PlayerMoveUseCase {
    override suspend operator fun invoke(
        player: Player,
    ) {
        playerPositionRepository.setPlayerPosition(
            player = player.move(),
        )
    }
}
