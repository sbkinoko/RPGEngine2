package common.repository.player

import common.status.PlayerStatus
import common.status.param.HP
import common.status.param.MP
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class PlayerRepositoryImpl : PlayerRepository {
    override val mutablePlayersFlow: MutableSharedFlow<List<PlayerStatus>> =
        MutableSharedFlow(replay = 1)

    override var players: List<PlayerStatus>

    init {
        players = listOf(
            PlayerStatus(
                name = "test1",
                hp = HP(
                    maxValue = 100,
                    value = 50,
                ),
                mp = MP(
                    maxValue = 10,
                    value = 5,
                )
            ),
            PlayerStatus(
                name = "test2",
                hp = HP(
                    maxValue = 100,
                    value = 0,
                ),
                mp = MP(
                    maxValue = 111,
                    value = 50,
                )
            ),
//            PlayerStatus(
//                name = "HPたくさん",
//                hp = HP(
//                    maxValue = 200,
//                    value = 50,
//                ),
//                mp = MP(
//                    maxValue = 10,
//                    value = 50,
//                )
//            ),
//            PlayerStatus(
//                name = "MPたくさん",
//                hp = HP(
//                    maxValue = 10,
//                    value = 50,
//                ),
//                mp = MP(
//                    maxValue = 100,
//                    value = 50,
//                )
//            )
        )

        CoroutineScope(Dispatchers.Default).launch {
            mutablePlayersFlow.emit(players)
        }
    }

    override fun setPlayer(id: Int, status: PlayerStatus) {
        val list = players.mapIndexed { index, playerStatus ->
            if (index == id) {
                status
            } else {
                playerStatus
            }
        }
        players = list
        CoroutineScope(Dispatchers.Default).launch {
            mutablePlayersFlow.emit(players)
        }
    }

    override fun getPlayer(id: Int): PlayerStatus {
        return players[id]
    }
}
