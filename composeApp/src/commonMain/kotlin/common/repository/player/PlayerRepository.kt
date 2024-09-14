package common.repository.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import common.repository.status.StatusRepository
import common.status.PlayerStatus
import kotlinx.coroutines.flow.MutableSharedFlow

interface PlayerRepository : StatusRepository<PlayerStatus> {
    val mutablePlayersFlow: MutableSharedFlow<List<PlayerStatus>>

    @Composable
    fun getFlowAsState(): State<List<PlayerStatus>>

    fun getPlayers(): List<PlayerStatus>

}
