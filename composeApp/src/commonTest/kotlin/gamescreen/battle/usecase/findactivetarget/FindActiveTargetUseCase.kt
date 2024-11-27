package gamescreen.battle.usecase.findactivetarget

import core.domain.status.Status

interface FindActiveTargetUseCase {

    operator fun invoke(
        statusList: List<Status>,
        target: Int,
        targetNum: Int,
    ): List<Int>
}
