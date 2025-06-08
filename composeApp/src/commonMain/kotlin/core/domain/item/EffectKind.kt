package core.domain.item

import core.domain.status.ConditionType

sealed interface EffectKind : Item

/**
 * 回復itemに必要な変数
 */
interface HealItem : EffectKind {
    /**
     * 回復量
     */
    val healAmount: Int
}

interface AttackItem : EffectKind {
    val damageType: DamageType
}

interface ConditionItem : EffectKind {
    val conditionType: ConditionType
}
