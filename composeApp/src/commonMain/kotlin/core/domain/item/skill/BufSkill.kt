package core.domain.item.skill

import core.domain.Place
import core.domain.item.BufEffect
import core.domain.item.CostType
import core.domain.item.Skill
import core.domain.item.TargetStatusType
import core.domain.item.TargetType
import core.domain.status.param.BufType
import gamescreen.battle.domain.StatusType

data class BufSkill(
    override val name: String,
    override val targetNum: Int,
    override val costType: CostType,
    override val usablePlace: Place,
    override val targetStatusType: TargetStatusType,
    override val targetType: TargetType,
    override val targetStatus: StatusType,
    override val bufType: BufType,
    override val amount: Int,
) : Skill, BufEffect {
    override val explain: String
        get() {
            return name
        }
}
