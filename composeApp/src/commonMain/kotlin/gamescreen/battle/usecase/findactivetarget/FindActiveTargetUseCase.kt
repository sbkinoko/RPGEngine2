package gamescreen.battle.usecase.findactivetarget

import core.domain.status.StatusData

interface FindActiveTargetUseCase {

    operator fun invoke(
        statusList: List<StatusData>,
        target: Int,
        targetNum: Int,
    ): List<Int>
}
