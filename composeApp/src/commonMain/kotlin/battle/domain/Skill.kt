package battle.domain

import core.domain.Place
import core.domain.status.Status

sealed interface Skill {
    val name: String
    val needMP: Int
    val targetNum: Int
    val canUse: (Int) -> Boolean
    val usablePlace: Place
}

// 必要になったらattackのinterfaceを作る
data class AttackSkill(
    override val name: String,
    override val needMP: Int,
    override val targetNum: Int,
    override val canUse: (Int) -> Boolean = { mp -> mp >= needMP },
    override val usablePlace: Place,
    val damageAmount: Int,
) : Skill

data class HealSkill(
    override val name: String,
    override val needMP: Int,
    override val targetNum: Int,
    override val canUse: (Int) -> Boolean = { mp -> mp >= needMP },
    override val usablePlace: Place,
    val healAmount: Int,
    val targetType: TargetType,
) : Skill

enum class TargetType {
    ACTIVE {
        override fun canSelect(status: Status): Boolean {
            return status.isActive
        }
    },

    INACTIVE {
        override fun canSelect(status: Status): Boolean {
            return status.isActive.not()
        }
    }

    ;

    /**
     * 対象のstatusが選択可能かどうかを返す
     */
    abstract fun canSelect(status: Status): Boolean
}
