package battle.service

import main.status.Status

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
