package data.status

import core.domain.status.PlayerStatus
import core.domain.status.StatusData
import core.domain.status.StatusType

interface StatusRepository {

    fun getStatus(
        id: Int,
        level: Int,
    ): Pair<PlayerStatus, StatusData<StatusType.Player>>
}
