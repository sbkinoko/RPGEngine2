package battle.service

import common.status.MonsterStatus

interface FindTargetService {

    fun findNext(
        monsters: List<MonsterStatus>,
        target: Int,
    ): Int

    fun findPrev(
        monsters: List<MonsterStatus>,
        target: Int,
    ): Int
}
