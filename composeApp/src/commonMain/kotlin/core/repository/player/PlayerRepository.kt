package core.repository.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import core.domain.status.PlayerStatus
import core.repository.status.StatusRepository
import kotlinx.coroutines.flow.MutableSharedFlow

interface PlayerRepository : StatusRepository<PlayerStatus> {
    val mutablePlayersFlow: MutableSharedFlow<List<PlayerStatus>>

    @Composable
    fun getFlowAsState(): State<List<PlayerStatus>>

    fun getPlayers(): List<PlayerStatus>

}
