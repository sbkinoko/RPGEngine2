package data.status

import core.domain.status.PlayerStatus
import core.domain.status.param.HP
import core.domain.status.param.MP

abstract class StatusRepositoryAbstract : StatusRepository {

    override fun getStatus(id: Int, level: Int): PlayerStatus {
        val playerStatus = statusBaseList[id]
        var statusSum = playerStatus

        for (lv: Int in 0 until level) {
            statusUpList[id][lv].apply {
                statusSum = statusSum.copy(
                    hp = HP(
                        statusSum.hp.maxValue + hp,
                    ),
                    mp = MP(
                        statusSum.mp.maxValue + mp,
                    )
                )
            }
        }

        return statusSum
    }

    protected abstract val statusUpList: List<List<StatusIncrease>>


    protected abstract val statusBaseList: List<PlayerStatus>
}
