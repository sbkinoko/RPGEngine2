package core.domain.item.skill

import core.domain.Place
import core.domain.item.ConditionEffect
import core.domain.item.CostType
import core.domain.item.NeedTarget
import core.domain.item.Skill
import core.domain.item.TargetStatusType
import core.domain.item.TargetType
import core.domain.item.UsableItem
import core.domain.status.ConditionType

class ConditionSkill(
    override val name: String,
    override val targetNum: Int,
    override val costType: CostType,
    override val usablePlace: Place,
    override val conditionType: ConditionType,
    override val targetType: TargetType,
    override val targetStatusType: TargetStatusType,
) : Skill, UsableItem, NeedTarget, ConditionEffect {
    override val explain: String
        get() {
            return name
        }
}
