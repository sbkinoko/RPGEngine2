package core.repository.memory.character.player

import core.domain.status.PlayerStatus
import data.repository.item.skill.SkillId
import data.repository.item.tool.ToolId
import data.repository.status.StatusRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import values.Constants

class PlayerCharacterRepositoryImpl(
    private val statusRepository: StatusRepository,
) : PlayerCharacterRepository {
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
            ).first
        }
    }

    override suspend fun setStatus(
        id: Int,
        status: PlayerStatus,
    ) {
        val list = players
            .mapIndexed { index, playerStatus ->
                if (index == id) {
                    status
                } else {
                    playerStatus
                }
            }
        players = list

        mutableStatusListFlow.value = players
    }

    override suspend fun setStatusList(status: List<PlayerStatus>) {
        players = status
        mutableStatusListFlow.value = players
    }

    override fun getStatus(id: Int): PlayerStatus {
        return players[id]
    }

    override fun getStatusList(): List<PlayerStatus> {
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
