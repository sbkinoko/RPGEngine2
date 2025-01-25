package gamescreen.map.repository.player

import gamescreen.map.domain.Player
import kotlinx.coroutines.flow.StateFlow

interface PlayerPositionRepository {
    val playerPositionStateFlow: StateFlow<Player>

    fun getPlayerPosition(): Player

    suspend fun setPlayerPosition(player: Player)

}
