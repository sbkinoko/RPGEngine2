package data.status

import core.domain.status.PlayerStatus

interface StatusRepository {

    fun getStatus(
        id: Int,
        level: Int,
    ): PlayerStatus
}
