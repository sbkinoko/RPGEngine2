package common.repositoryImpl

import common.repository.PlayerRepository
import common.status.PlayerStatus
import common.status.param.HP
import common.status.param.MP

class PlayerRepositoryImpl : PlayerRepository {
    override fun getPlayer(id: Int): PlayerStatus {
        return when (id) {
            0 -> PlayerStatus(
                name = "test1",
                hp = HP(
                    maxValue = 100,
                    value = 50,
                ),
                mp = MP(
                    maxValue = 10,
                    value = 5,
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
                )
            )

            2 -> PlayerStatus(
                name = "HPたくさん",
                hp = HP(
                    maxValue = 200,
                    value = 50,
                ),
                mp = MP(
                    maxValue = 10,
                    value = 50,
                )
            )

            else -> PlayerStatus(
                name = "MPたくさん",
                hp = HP(
                    maxValue = 10,
                    value = 50,
                ),
                mp = MP(
                    maxValue = 100,
                    value = 50,
                )
            )
        }
    }
}
