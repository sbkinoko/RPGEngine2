package gamescreen.map.repository.player

import gamescreen.map.domain.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayerPositionRepositoryImpl : PlayerPositionRepository {
    private val mutablePlayerPositionStateFlow =
        MutableStateFlow(
            Player(
                size = 0f,
            )
        )

    override val playerPositionStateFlow: StateFlow<Player>
        get() = mutablePlayerPositionStateFlow.asStateFlow()

    override fun getPlayerPosition(): Player {
        return playerPositionStateFlow.value
    }

    override suspend fun setPlayerPosition(player: Player) {
        mutablePlayerPositionStateFlow.value = player
    }
}
