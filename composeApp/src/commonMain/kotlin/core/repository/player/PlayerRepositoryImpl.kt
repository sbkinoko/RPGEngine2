package core.repository.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import common.values.playerNum
import core.domain.status.PlayerStatus
import core.domain.status.param.HP
import core.domain.status.param.MP
import core.repository.skill.ATTACK_TO_2
import core.repository.skill.CANT_USE
import core.repository.skill.HEAL_SKILL
import core.repository.skill.REVIVE_SKILL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class PlayerRepositoryImpl : PlayerRepository {
    override val mutablePlayersFlow: MutableSharedFlow<List<PlayerStatus>> =
        MutableSharedFlow(replay = 1)

    @Composable
    override fun getFlowAsState(): State<List<PlayerStatus>> {
        return mutablePlayersFlow.collectAsState(
            players
        )
    }

    private var players: List<PlayerStatus>

    init {
        players = List(playerNum) {
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
                )

                else -> throw IllegalStateException()
            }
        }

        CoroutineScope(Dispatchers.Default).launch {
            mutablePlayersFlow.emit(players)
        }
    }

    override suspend fun setStatus(id: Int, status: PlayerStatus) {
        val list = players.mapIndexed { index, playerStatus ->
            if (index == id) {
                status
            } else {
                playerStatus
            }
        }
        players = list

        mutablePlayersFlow.emit(players)
    }

    override fun getStatus(id: Int): PlayerStatus {
        return players[id]
    }

    override fun getPlayers(): List<PlayerStatus> {
        return players
    }
}
