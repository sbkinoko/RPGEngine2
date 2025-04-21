package gamescreen.battle.usecase.findactivetarget

import core.domain.status.Character

interface FindActiveTargetUseCase {

    operator fun invoke(
        statusList: List<Character>,
        target: Int,
        targetNum: Int,
    ): List<Int>
}
