package core.domain.item

import core.domain.status.ConditionType

sealed interface TypeKind : Item

/**
 * 回復itemに必要な変数
 */
interface HealItem : TypeKind {
    /**
     * 回復量
     */
    val healAmount: Int

    /**
     * 回復対象にとれる状態
     */
    val targetType: TargetType
}

interface AttackItem : TypeKind {
    val damageType: DamageType
}

interface ConditionItem : TypeKind {
    val conditionType: ConditionType
}
