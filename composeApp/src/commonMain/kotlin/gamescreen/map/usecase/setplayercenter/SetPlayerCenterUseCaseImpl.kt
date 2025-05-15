package gamescreen.map.usecase.setplayercenter

import gamescreen.map.domain.Player
import gamescreen.map.usecase.GetScreenCenterUseCase
import gamescreen.map.usecase.PlayerMoveToUseCase

class SetPlayerCenterUseCaseImpl(
    private val getScreenCenterUseCase: GetScreenCenterUseCase,
    private val playerMoveToUseCase: PlayerMoveToUseCase,
) : SetPlayerCenterUseCase {
    override suspend fun invoke(
        player: Player,
    ): Player {
        val center = getScreenCenterUseCase.invoke()
        return playerMoveToUseCase.invoke(
            player = player,
            x = center.x - player.size / 2,
            y = center.y - player.size / 2,
        )
    }
}
