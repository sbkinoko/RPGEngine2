package core.domain.item.skill

import core.domain.Place
import core.domain.item.ConditionItem
import core.domain.item.CostType
import core.domain.item.Skill
import core.domain.status.ConditionType

class ConditionSkill(
    override val name: String,
    override val targetNum: Int,
    override val costType: CostType,
    override val usablePlace: Place,
    override val conditionType: ConditionType,
) : Skill, ConditionItem {
    override val explain: String
        get() {
            return name
        }
}
