package core.repository.player

import core.domain.status.PlayerStatus
import core.domain.status.param.HP
import core.domain.status.param.MP
import core.repository.item.skill.ATTACK_TO_2
import core.repository.item.skill.CANT_USE
import core.repository.item.skill.HEAL_SKILL
import core.repository.item.skill.REVIVE_SKILL
import core.repository.item.tool.ToolRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import values.Constants

class PlayerStatusRepositoryImpl : PlayerStatusRepository {
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
            when (it) {
                0 -> PlayerStatus(
                    name = "test1",
                    hp = HP(
                        maxValue = 100,
                        value = 50,
                    ),
                    mp = MP(
                        maxValue = 10,
                        value = 5,
                    ),
                    skillList = listOf(
                        ATTACK_TO_2,
                        CANT_USE,
                        HEAL_SKILL,
                        REVIVE_SKILL,
                    ),
                    toolList = listOf(
                        ToolRepositoryImpl.HEAL_TOOL,
                        ToolRepositoryImpl.HEAL_TOOL,
                        ToolRepositoryImpl.HEAL_TOOL2,
                        ToolRepositoryImpl.HEAL_TOOL,
                        ToolRepositoryImpl.HEAL_TOOL,
                        ToolRepositoryImpl.HEAL_TOOL,
                    )
                )

                1 -> PlayerStatus(
                    name = "test2",
                    hp = HP(
                        maxValue = 100,
                        value = 0,
                    ),
                    mp = MP(
                        maxValue = 111,
                        value = 50,
                    ),
                    skillList = listOf(
                        ATTACK_TO_2,
                        CANT_USE,
                    ),
                    toolList = listOf(
                        ToolRepositoryImpl.HEAL_TOOL,
                        ToolRepositoryImpl.HEAL_TOOL,
                    )
                )

                2 -> PlayerStatus(
                    name = "test3",
                    hp = HP(
                        maxValue = 100,
                        value = 50,
                    ),
                    mp = MP(
                        maxValue = 10,
                        value = 10
                    ),
                    skillList = listOf(
                        HEAL_SKILL,
                        REVIVE_SKILL,
                    ),
                    toolList = listOf(
                        ToolRepositoryImpl.HEAL_TOOL,
                        ToolRepositoryImpl.HEAL_TOOL,
                        ToolRepositoryImpl.HEAL_TOOL,
                    )
                )

                3 -> PlayerStatus(
                    name = "MPたくさん",
                    hp = HP(
                        maxValue = 10,
                        value = 50,
                    ),
                    mp = MP(
                        maxValue = 100,
                        value = 50,
                    ),
                    skillList = listOf(
                        HEAL_SKILL,
                        REVIVE_SKILL,
                    ),
                    toolList = listOf(
                        ToolRepositoryImpl.HEAL_TOOL,
                        ToolRepositoryImpl.HEAL_TOOL,
                        ToolRepositoryImpl.HEAL_TOOL,
                        ToolRepositoryImpl.HEAL_TOOL,
                    )
                )

                else -> throw IllegalStateException()
            }
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
    ): Int {
        val player = players[playerId]
        return player.toolList[index]
    }

    override fun getSkill(
        playerId: Int,
        index: Int,
    ): Int {
        val player = players[playerId]
        return player.skillList[index]
    }
}
