package core.domain.item.skill

import core.domain.Place
import core.domain.item.AttackItem
import core.domain.item.Skill

data class AttackSkill(
    override val name: String,
    override val needMP: Int,
    override val targetNum: Int,
    override val canUse: (Int) -> Boolean = { mp -> mp >= needMP },
    override val usablePlace: Place,
    override val damageAmount: Int,
) : Skill, AttackItem {
    override val explain: String
        get() {
            return name
        }
}
