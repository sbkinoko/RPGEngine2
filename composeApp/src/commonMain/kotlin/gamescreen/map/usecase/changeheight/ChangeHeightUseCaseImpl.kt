package gamescreen.map.usecase.changeheight

import gamescreen.map.domain.ObjectHeight
import gamescreen.map.domain.Player
import gamescreen.map.domain.collision.square.NormalRectangle

// todo test作る
class ChangeHeightUseCaseImpl : ChangeHeightUseCase {
    override suspend fun invoke(
        targetHeight: ObjectHeight,
        player: Player,
    ): Player {
        val heightUpdatedPlayer = player.copy(
            square = (player.square as NormalRectangle).copy(
                objectHeight = targetHeight,
            )
        )

        return heightUpdatedPlayer
    }
}
