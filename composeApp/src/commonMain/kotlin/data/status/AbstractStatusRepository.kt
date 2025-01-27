package data.status

import core.domain.status.PlayerStatus
import core.domain.status.StatusIncrease

abstract class AbstractStatusRepository : StatusRepository {

    override fun getStatus(id: Int, level: Int): PlayerStatus {
        var statusSum = statusBaseList[id]

        for (lv: Int in 0 until level) {
            // fixme 本来はいらないがテスト段階では必要
            if (statusUpList[id].size <= lv) {
                break
            }

            statusSum = statusSum.addStatus(
                statusIncrease = statusUpList[id][lv],
            )
        }

        return statusSum
    }

    protected abstract val statusUpList: List<List<StatusIncrease>>


    protected abstract val statusBaseList: List<PlayerStatus>
}
