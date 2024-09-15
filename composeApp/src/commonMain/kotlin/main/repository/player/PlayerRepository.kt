package main.repository.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.MutableSharedFlow
import main.repository.status.StatusRepository
import main.status.PlayerStatus

interface PlayerRepository : StatusRepository<PlayerStatus> {
    val mutablePlayersFlow: MutableSharedFlow<List<PlayerStatus>>

    @Composable
    fun getFlowAsState(): State<List<PlayerStatus>>

    fun getPlayers(): List<PlayerStatus>

}
