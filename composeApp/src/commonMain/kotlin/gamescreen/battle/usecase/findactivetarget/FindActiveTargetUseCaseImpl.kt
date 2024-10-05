package gamescreen.battle.usecase.findactivetarget

import core.domain.status.Status
import gamescreen.battle.service.FindTargetService

class FindActiveTargetUseCaseImpl(
    private val findTargetService: FindTargetService,
) : FindActiveTargetUseCase {
    override fun invoke(
        statusList: List<Status>,
        target: Int,
        targetNum: Int,
    ): List<Int> {
        val targetList = mutableListOf<Int>()

        var tmpTarget = target
        for (i in 0 until targetNum) {
            while (statusList[tmpTarget].isActive.not()) {
                tmpTarget = findTargetService.findNext(
                    statusList = statusList,
                    target = tmpTarget,
                )
            }

            // 同じのは複数選択しない
            if (targetList.contains(tmpTarget)) {
                break
            }

            targetList.add(tmpTarget)
            // 次のモンスターを選択しておく
            tmpTarget = findTargetService.findNext(
                statusList = statusList,
                target = tmpTarget,
            )
        }

        return targetList
    }
}
