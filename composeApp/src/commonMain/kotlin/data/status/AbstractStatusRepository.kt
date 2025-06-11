package data.status

import core.domain.status.PlayerStatus
import core.domain.status.StatusIncrease

abstract class AbstractStatusRepository : StatusRepository {

    override fun getStatus(
        id: Int,
        level: Int,
    ): PlayerStatus {
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

        // fixme セーブするようになったらvalueをセーブデータから読み取る
        return statusSum.run {
            copy(
                statusData = statusData.copy(
                    hp = statusData.hp.set(value = Int.MAX_VALUE),
                    mp = statusData.mp.set(value = Int.MAX_VALUE),
                )
            )
        }
    }

    protected abstract val statusUpList: List<List<StatusIncrease>>

    protected abstract val statusBaseList: List<PlayerStatus>
}
