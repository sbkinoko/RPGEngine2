package core.domain.item.tool

import core.domain.Place
import core.domain.item.ItemKind
import core.domain.item.TargetType
import core.domain.item.TypeKind

data class HealTool(
    override val id: Int,
    override val name: String,
    override val targetNum: Int,
    override val usablePlace: Place,
    override val isDisposable: Boolean,
    override val isReusable: Boolean,
    override val healAmount: Int,
    override val targetType: TargetType,
) : TypeKind.HealItem, ItemKind.Tool {
    override val explain: String
        get() {
            return name + "\n" +
                    "${id}番目のアイテム"
        }
}
