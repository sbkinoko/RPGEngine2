package core.repository.player

import core.domain.status.PlayerStatus
import core.repository.status.StatusRepository
import kotlinx.coroutines.flow.StateFlow

interface PlayerStatusRepository : StatusRepository<PlayerStatus> {
    val playerStatusFlow: StateFlow<List<PlayerStatus>>

    fun getPlayers(): List<PlayerStatus>

    fun getTool(playerId: Int, index: Int): Int

    fun getSkill(playerId: Int, index: Int): Int
}
