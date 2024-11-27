package gamescreen.battle.service

import core.domain.status.Status

class FindTargetServiceImpl : FindTargetService {

    override fun findNext(
        statusList: List<Status>,
        target: Int,
    ): Int {
        var actualTarget = target + 1
        if (statusList.size <= actualTarget) {
            actualTarget = 0
        }

        //　戦闘不能じゃないtargetを探す
        while (statusList[actualTarget].isActive.not()) {
            actualTarget = collectTarget(
                target = actualTarget + 1,
                size = statusList.size,
            )
        }
        return actualTarget
    }

    override fun findPrev(
        statusList: List<Status>,
        target: Int,
    ): Int {
        var actualTarget = collectTarget(
            target = target - 1,
            size = statusList.size,
        )

        //　戦闘不能じゃないtargetを探す
        while (statusList[actualTarget].isActive.not()) {
            actualTarget = collectTarget(
                target = actualTarget - 1,
                size = statusList.size,
            )
        }
        return actualTarget
    }

    private fun collectTarget(
        target: Int,
        size: Int,
    ): Int {
        return if (target < 0) {
            size - 1
        } else if (size <= target) {
            0
        } else {
            target
        }
    }
}
