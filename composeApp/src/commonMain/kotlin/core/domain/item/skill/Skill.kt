package core.domain.item.skill

import core.domain.Place
import core.domain.item.Item
import core.domain.item.TargetType

sealed interface Skill : Item {
    val needMP: Int

    // fixme コストのタイプをする
    val canUse: (Int) -> Boolean
}

// 必要になったらattackのinterfaceを作る
data class AttackSkill(
    override val id: Int,
    override val name: String,
    override val needMP: Int,
    override val targetNum: Int,
    override val canUse: (Int) -> Boolean = { mp -> mp >= needMP },
    override val usablePlace: Place,
    val damageAmount: Int,
) : Skill {
    override val explain: String
        get() {
            return name + "\n" +
                    "${id}番目のスキル"
        }
}

data class HealSkill(
    override val id: Int,
    override val name: String,
    override val needMP: Int,
    override val targetNum: Int,
    override val canUse: (Int) -> Boolean = { mp -> mp >= needMP },
    override val usablePlace: Place,
    val healAmount: Int,
    val targetType: TargetType,
) : Skill {
    override val explain: String
        get() {
            return name + "\n" +
                    "${id}番目のスキル"
        }
}
