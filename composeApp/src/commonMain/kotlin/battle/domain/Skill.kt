package battle.domain

data class Skill(
    val name: String,
    val needMP: Int,
    val targetNum: Int,
    val canUse: (Int) -> Boolean,
)
