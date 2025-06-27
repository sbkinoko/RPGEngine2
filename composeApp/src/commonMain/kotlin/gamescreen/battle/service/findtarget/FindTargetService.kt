package gamescreen.battle.service.findtarget

import core.domain.status.StatusData

interface FindTargetService {

    fun findNext(
        statusList: List<StatusData>,
        target: Int,
    ): Int

    fun findPrev(
        statusList: List<StatusData>,
        target: Int,
    ): Int
}
