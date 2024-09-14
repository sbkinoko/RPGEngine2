package battle.domain

interface Skill {
    val name: String
    val needMP: Int
    val targetNum: Int
    val canUse: (Int) -> Boolean
}

// 必要になったらattackのinterfaceを作る
data class AttackSkill(
    override val name: String,
    override val needMP: Int,
    override val targetNum: Int,
    override val canUse: (Int) -> Boolean = { mp -> mp >= needMP },
    val damageAmount: Int,
) : Skill

data class HealSkill(
    override val name: String,
    override val needMP: Int,
    override val targetNum: Int,
    override val canUse: (Int) -> Boolean = { mp -> mp >= needMP },
    val healAmount: Int,
) : Skill
