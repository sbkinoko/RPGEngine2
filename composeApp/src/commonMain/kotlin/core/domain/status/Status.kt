package core.domain.status

import core.domain.status.param.HP
import core.domain.status.param.MP

interface Status {
    var name: String

    val hp: HP

    val mp: MP

    val speed: Int

    val isActive: Boolean
        get() {
            return 0 < hp.point
        }

    val conditionList: List<ConditionType>
}
