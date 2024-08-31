package battle.usecase.findactivetarget

import common.status.Status

class FindActiveTargetUseCaseImpl() : FindActiveTargetUseCase {
    override fun invoke(
        statusList: List<Status>,
        target: Int,
        targetNum: Int,
    ): List<Int> {
        TODO("Not yet implemented")
    }
}
