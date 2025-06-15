package data.status

import core.domain.status.PlayerStatus
import core.domain.status.StatusData
import core.domain.status.StatusIncrease
import core.domain.status.StatusType

abstract class AbstractStatusRepository : StatusRepository {

    override fun getStatus(
        id: Int,
        level: Int,
    ): Pair<PlayerStatus, StatusData<StatusType.Player>> {
        var (playerStatus, statusData) = statusBaseList[id]

        for (lv: Int in 0 until level) {
            // fixme 本来はいらないがテスト段階では必要
            if (statusUpList[id].size <= lv) {
                break
            }

            statusData = statusData.incStatus(
                statusIncrease = statusUpList[id][lv],
            )

            //　レベルごとに技を変えるならここをいじる
        }

        // fixme セーブするようになったらvalueをセーブデータから読み取る
        return Pair(
            playerStatus,
            statusData.copy(
                hp = statusData.hp.set(value = Int.MAX_VALUE),
                mp = statusData.mp.set(value = Int.MAX_VALUE),
            )
        )
    }

    protected abstract val statusUpList: List<List<StatusIncrease>>

    protected abstract val statusBaseList: List<Pair<PlayerStatus, StatusData<StatusType.Player>>>
}
