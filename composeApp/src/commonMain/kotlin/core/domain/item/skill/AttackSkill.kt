package core.domain.item.skill

import core.domain.Place
import core.domain.item.AttackItem
import core.domain.item.CostType
import core.domain.item.Skill

data class AttackSkill(
    override val name: String,
    override val targetNum: Int,
    override val costType: CostType,
    override val usablePlace: Place,
    override val damageAmount: Int,
) : Skill, AttackItem {
    override val explain: String
        get() {
            return name
        }
}
