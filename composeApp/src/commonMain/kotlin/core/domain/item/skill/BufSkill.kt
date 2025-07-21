package core.domain.item.skill

import core.domain.Place
import core.domain.item.BufEffect
import core.domain.item.CostType
import core.domain.item.NeedTarget
import core.domain.item.Skill
import core.domain.item.TargetStatusType
import core.domain.item.TargetType
import core.domain.item.UsableItem
import core.domain.status.param.Buf
import core.domain.status.param.ParameterType

data class BufSkill(
    override val name: String,
    override val targetNum: Int,
    override val costType: CostType,
    override val usablePlace: Place,
    override val targetStatusType: TargetStatusType,
    override val targetType: TargetType,
    override val parameterType: ParameterType,
    override val buf: Buf<*>,
) : Skill, NeedTarget, UsableItem, BufEffect {
    override val explain: String
        get() {
            return name
        }
}
