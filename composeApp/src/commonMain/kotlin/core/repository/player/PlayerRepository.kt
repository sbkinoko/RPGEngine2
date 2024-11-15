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

    fun getTool(playerId: Int, index: Int): Int

    fun getSkill(playerId: Int, index: Int): Int
}
