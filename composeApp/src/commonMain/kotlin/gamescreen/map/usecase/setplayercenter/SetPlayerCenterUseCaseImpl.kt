package gamescreen.map.usecase.setplayercenter

import gamescreen.map.domain.Player
import gamescreen.map.usecase.GetScreenCenterUseCase

class SetPlayerCenterUseCaseImpl(
    private val getScreenCenterUseCase: GetScreenCenterUseCase,
) : SetPlayerCenterUseCase {
    override suspend fun invoke(
        player: Player,
    ): Player {
        val center = getScreenCenterUseCase.invoke()
        return player.moveTo(
            x = center.x - player.size / 2,
            y = center.y - player.size / 2,
        )
    }
}
