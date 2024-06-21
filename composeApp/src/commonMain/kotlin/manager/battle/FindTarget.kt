package manager.battle

import domain.common.status.MonsterStatus

class FindTarget {

    fun find(
        monsters: List<MonsterStatus>,
        target: Int,
    ): Int {
        var actualTarget = target

        //　戦闘不能じゃないtargetを探す
        while (monsters[actualTarget].isActive.not()) {
            actualTarget++
            if (monsters.size <= actualTarget) {
                actualTarget = 0
            }
        }
        return actualTarget
    }
}
