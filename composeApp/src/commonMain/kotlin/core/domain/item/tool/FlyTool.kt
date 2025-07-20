package core.domain.item.tool

import core.domain.Place
import core.domain.item.CostType
import core.domain.item.FlyEffect
import core.domain.item.TargetStatusType
import core.domain.item.TargetType
import core.domain.item.Tool

data class FlyTool(
    override val name: String,
    override val targetNum: Int,
    override val usablePlace: Place,
    override val isDisposable: Boolean,
    override val costType: CostType,
    override val targetStatusType: TargetStatusType,
    override val targetType: TargetType,
) : FlyEffect, Tool {
    override val explain: String
        get() {
            return name
        }
}
