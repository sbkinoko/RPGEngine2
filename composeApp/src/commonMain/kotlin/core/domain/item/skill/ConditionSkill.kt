package core.domain.item.skill

import core.domain.Place
import core.domain.item.AbnormalConditionItem
import core.domain.item.Skill
import core.domain.status.ConditionType

class ConditionSkill(
    override val name: String,
    override val needMP: Int,
    override val targetNum: Int,
    override val canUse: (Int) -> Boolean = { mp -> mp >= needMP },
    override val usablePlace: Place,
    override val conditionType: ConditionType,
) : Skill, AbnormalConditionItem {
    override val explain: String
        get() {
            return name
        }
}
