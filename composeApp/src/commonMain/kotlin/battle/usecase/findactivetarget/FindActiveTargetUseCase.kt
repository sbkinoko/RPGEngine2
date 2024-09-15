package battle.usecase.findactivetarget

import main.status.Status

interface FindActiveTargetUseCase {

    operator fun invoke(
        statusList: List<Status>,
        target: Int,
        targetNum: Int,
    ): List<Int>
}
