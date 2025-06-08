package core.domain.item.tool

import core.domain.Place
import core.domain.item.CostType
import core.domain.item.HealItem
import core.domain.item.TargetStatusType
import core.domain.item.Tool

data class HealTool(
    override val name: String,
    override val targetNum: Int,
    override val usablePlace: Place,
    override val isDisposable: Boolean,
    override val costType: CostType,
    override val healAmount: Int,
    override val targetStatusType: TargetStatusType,
) : HealItem, Tool {
    override val explain: String
        get() {
            return name
        }
}
