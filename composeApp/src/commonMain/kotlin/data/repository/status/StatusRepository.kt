package data.repository.status

import core.domain.status.PlayerStatus
import core.domain.status.StatusData

interface StatusRepository {

    fun getStatus(
        id: Int,
        level: Int,
    ): Pair<PlayerStatus, StatusData>
}
