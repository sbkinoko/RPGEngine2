package core.repository.player

import core.domain.status.PlayerStatus
import data.item.skill.SkillId
import data.item.tool.ToolId
import data.status.StatusRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import values.Constants

class PlayerStatusRepositoryImpl(
    private val statusRepository: StatusRepository,
) : PlayerStatusRepository {
    private val mutableStatusListFlow: MutableStateFlow<List<PlayerStatus>>

    override val playerStatusFlow: StateFlow<List<PlayerStatus>>


    private var players: List<PlayerStatus>

    init {
        players = initialPlayer()

        mutableStatusListFlow =
            MutableStateFlow(
                players
            )

        playerStatusFlow = mutableStatusListFlow.asStateFlow()
    }

    private fun initialPlayer(): List<PlayerStatus> {
        return List(Constants.playerNum) {
            statusRepository.getStatus(
                id = it,
                level = 1,
            )
        }
    }

    override suspend fun setStatus(id: Int, status: PlayerStatus) {
        val list = players
            .mapIndexed { index, playerStatus ->
                if (index == id) {
                    status
                } else {
                    playerStatus
                }
            }
        players = list

        mutableStatusListFlow.emit(players)
    }

    override fun getStatus(id: Int): PlayerStatus {
        return players[id]
    }

    override fun getPlayers(): List<PlayerStatus> {
        return players
    }

    override fun getTool(
        playerId: Int,
        index: Int,
    ): ToolId {
        val player = players[playerId]
        return player.toolList[index]
    }

    override fun getSkill(
        playerId: Int,
        index: Int,
    ): SkillId {
        val player = players[playerId]
        return player.skillList[index]
    }
}
