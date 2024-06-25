package common.repository

import common.status.PlayerStatus

interface PlayerRepository {
    fun getPlayer(id: Int): PlayerStatus
}
