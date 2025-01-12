package gamescreen.map.usecase.setplayercenter

import gamescreen.map.domain.Player
import gamescreen.map.usecase.GetScreenCenterUseCase
import gamescreen.map.usecase.PlayerMoveToUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SetPlayerCenterUseCaseImpl(
    private val player: Player,
    private val getScreenCenterUseCase: GetScreenCenterUseCase,
    private val playerMoveToUseCase: PlayerMoveToUseCase,
) : SetPlayerCenterUseCase {
    override fun invoke() {
        CoroutineScope(Dispatchers.Default).launch {
            val center = getScreenCenterUseCase.invoke()
            playerMoveToUseCase.invoke(
                x = center.x - player.size / 2,
                y = center.y - player.size / 2,
            )
        }
    }
}
