package gamescreen.map.usecase.movetowater

import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.repository.player.PlayerPositionRepository

class MoveToWaterUseCaseImpl(
    private val playerPositionRepository: PlayerPositionRepository,
//    private val playerMoveToUseCase: PlayerMoveToUseCase,
//    private val isCollidedUseCase: IsCollidedUseCase,
) : MoveToWaterUseCase {
    override suspend fun invoke() {
        val player = playerPositionRepository.getPlayerPosition()

        val waterPlayer = player.copy(
            square = (player.square as NormalSquare).copy(
                objectHeight = ObjectHeight.Water
            )
        )

        playerPositionRepository.setPlayerPosition(
            waterPlayer
        )
    }
}
