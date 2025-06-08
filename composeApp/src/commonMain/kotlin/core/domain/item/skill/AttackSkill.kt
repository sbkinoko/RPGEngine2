package core.domain.item.skill

import core.domain.Place
import core.domain.item.AttackItem
import core.domain.item.CostType
import core.domain.item.DamageType
import core.domain.item.Skill
import core.domain.item.TargetStatusType
import core.domain.item.TargetType

data class AttackSkill(
    override val name: String,
    override val targetNum: Int,
    override val costType: CostType,
    override val usablePlace: Place,
    override val damageType: DamageType,
    override val targetType: TargetType,
    override val targetStatusType: TargetStatusType,
) : Skill, AttackItem {
    override val explain: String
        get() {
            return name
        }
}
