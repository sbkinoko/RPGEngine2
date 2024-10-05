package core.domain.item.tool

import core.domain.Place
import core.domain.item.Heal
import core.domain.item.Item
import core.domain.item.TargetType

data class HealTool(
    override val id: Int,
    override val name: String,
    override val targetNum: Int,
    override val usablePlace: Place,
    override val healAmount: Int,
    override val targetType: TargetType,
) : Item, Heal {
    override val explain: String
        get() {
            return name + "\n" +
                    "${id}番目のスキル"
        }
}
