package core.domain.item.tool

import core.domain.Place
import core.domain.item.HealItem
import core.domain.item.TargetType

data class HealTool(
    override val id: Int,
    override val name: String,
    override val targetNum: Int,
    override val usablePlace: Place,
    override val isDisposable: Boolean,
    override val isReusable: Boolean,
    override val healAmount: Int,
    override val targetType: TargetType,
) : Tool, HealItem {
    override val explain: String
        get() {
            return name + "\n" +
                    "${id}番目のアイテム"
        }
}
