package gamescreen.battle.service.findtarget

import core.domain.status.Character

interface FindTargetService {

    fun findNext(
        statusList: List<Character>,
        target: Int,
    ): Int

    fun findPrev(
        statusList: List<Character>,
        target: Int,
    ): Int
}
