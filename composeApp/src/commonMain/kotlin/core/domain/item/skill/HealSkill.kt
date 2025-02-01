package core.domain.item.skill

import core.domain.Place
import core.domain.item.HealItem
import core.domain.item.Skill
import core.domain.item.TargetType

data class HealSkill(
    override val name: String,
    override val needMP: Int,
    override val targetNum: Int,
    override val canUse: (Int) -> Boolean = { mp -> mp >= needMP },
    override val usablePlace: Place,
    override val healAmount: Int,
    override val targetType: TargetType,
) : Skill, HealItem {
    override val explain: String
        get() {
            return name
        }
}
