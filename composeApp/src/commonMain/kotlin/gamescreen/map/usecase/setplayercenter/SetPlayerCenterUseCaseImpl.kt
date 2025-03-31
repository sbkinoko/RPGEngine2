package gamescreen.map.usecase.setplayercenter

import gamescreen.map.domain.Player
import gamescreen.map.usecase.GetScreenCenterUseCase
import gamescreen.map.usecase.PlayerMoveToUseCase

class SetPlayerCenterUseCaseImpl(
    private val player: Player,
    private val getScreenCenterUseCase: GetScreenCenterUseCase,
    private val playerMoveToUseCase: PlayerMoveToUseCase,
) : SetPlayerCenterUseCase {
    override suspend fun invoke(): Player {
        val center = getScreenCenterUseCase.invoke()
        return playerMoveToUseCase.invoke(
            x = center.x - player.size / 2,
            y = center.y - player.size / 2,
        )
    }
}
