package core.domain.status

import core.domain.status.param.HP
import core.domain.status.param.MP

interface Status {
    var name: String

    // fixme 変更用の関数を作る
    val hp: HP

    // fixme 変更用の関数を作る
    val mp: MP

    val speed: Int

    val isActive: Boolean
        get() {
            return 0 < hp.point
        }

    val conditionList: List<ConditionType>
    fun updateConditionList(conditionList: List<ConditionType>): Status
}
