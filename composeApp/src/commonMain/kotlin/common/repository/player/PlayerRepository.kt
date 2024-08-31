package common.repository.player

import common.status.PlayerStatus
import kotlinx.coroutines.flow.MutableSharedFlow

interface PlayerRepository {
    val mutablePlayersFlow: MutableSharedFlow<List<PlayerStatus>>

    var players: List<PlayerStatus>

    fun getPlayer(id: Int): PlayerStatus

    fun setPlayer(
        id: Int,
        status: PlayerStatus,
    )
}
