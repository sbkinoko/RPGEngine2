package core.domain.item.tool

import core.domain.Place
import core.domain.item.CostType
import core.domain.item.FlyEffect
import core.domain.item.Tool
import core.domain.item.UsableItem

data class FlyTool(
    override val name: String,
    override val usablePlace: Place,
    override val isDisposable: Boolean,
    override val costType: CostType,
) : FlyEffect, UsableItem, Tool {
    override val explain: String
        get() {
            return name
        }
}
