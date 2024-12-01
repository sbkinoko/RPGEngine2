package gamescreen.battle.service.findtarget

import core.domain.status.Status

interface FindTargetService {

    fun findNext(
        statusList: List<Status>,
        target: Int,
    ): Int

    fun findPrev(
        statusList: List<Status>,
        target: Int,
    ): Int
}
