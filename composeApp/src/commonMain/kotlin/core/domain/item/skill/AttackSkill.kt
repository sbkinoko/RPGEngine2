package core.domain.item.skill

import core.domain.Place
import core.domain.item.AttackItem

// 必要になったらattackのinterfaceを作る
data class AttackSkill(
    override val id: Int,
    override val name: String,
    override val needMP: Int,
    override val targetNum: Int,
    override val canUse: (Int) -> Boolean = { mp -> mp >= needMP },
    override val usablePlace: Place,
    override val damageAmount: Int,
) : Skill, AttackItem {
    override val explain: String
        get() {
            return name + "\n" +
                    "${id}番目のスキル"
        }
}
