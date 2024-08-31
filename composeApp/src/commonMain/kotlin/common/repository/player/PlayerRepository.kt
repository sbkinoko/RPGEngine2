package common.repository.player

import common.status.PlayerStatus

interface PlayerRepository {
    fun getPlayer(id: Int): PlayerStatus
}
