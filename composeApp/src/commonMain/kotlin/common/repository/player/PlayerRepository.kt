package common.repository.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import common.status.PlayerStatus
import kotlinx.coroutines.flow.MutableSharedFlow

interface PlayerRepository {
    val mutablePlayersFlow: MutableSharedFlow<List<PlayerStatus>>

    @Composable
    fun getFlowAsState(): State<List<PlayerStatus>>

    fun getPlayer(id: Int): PlayerStatus

    fun setPlayer(
        id: Int,
        status: PlayerStatus,
    )
}
