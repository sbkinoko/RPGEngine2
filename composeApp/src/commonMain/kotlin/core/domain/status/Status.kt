package core.domain.status

import core.domain.status.param.statusParameterWithMax.HP
import core.domain.status.param.statusParameterWithMax.MP

interface Status {
    var name: String

    val hp: HP
    val mp: MP

    val speed: Int

    val isActive: Boolean
        get() {
            return 0 < hp.value
        }

    val conditionList: List<ConditionType>
    fun updateConditionList(conditionList: List<ConditionType>): Status
}
