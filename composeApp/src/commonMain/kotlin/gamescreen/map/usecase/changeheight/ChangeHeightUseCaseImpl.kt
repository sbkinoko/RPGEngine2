package gamescreen.map.usecase.changeheight

import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.collision.square.NormalSquare
import gamescreen.map.repository.player.PlayerPositionRepository

// todo test作る
class ChangeHeightUseCaseImpl(
    private val playerPositionRepository: PlayerPositionRepository,
) : ChangeHeightUseCase {
    override suspend fun invoke(
        targetHeight: ObjectHeight,
    ) {
        val player = playerPositionRepository.getPlayerPosition()

        val heightUpdatedPlayer = player.copy(
            square = (player.square as NormalSquare).copy(
                objectHeight = targetHeight,
            )
        )

        // 高さを保存
        playerPositionRepository.setPlayerPosition(
            heightUpdatedPlayer,
        )
    }
}
