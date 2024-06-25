package battle.serviceimpl

import battle.service.FindTargetService
import common.status.MonsterStatus

class FindTargetServiceImpl : FindTargetService {

    override fun findNext(
        monsters: List<MonsterStatus>,
        target: Int,
    ): Int {
        var actualTarget = target + 1
        if (monsters.size <= actualTarget) {
            actualTarget = 0
        }

        //　戦闘不能じゃないtargetを探す
        while (monsters[actualTarget].isActive.not()) {
            actualTarget = collectTarget(
                target = actualTarget + 1,
                size = monsters.size,
            )
        }
        return actualTarget
    }

    override fun findPrev(
        monsters: List<MonsterStatus>,
        target: Int,
    ): Int {
        var actualTarget = collectTarget(
            target = target - 1,
            size = monsters.size,
        )

        //　戦闘不能じゃないtargetを探す
        while (monsters[actualTarget].isActive.not()) {
            actualTarget = collectTarget(
                target = actualTarget - 1,
                size = monsters.size,
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
