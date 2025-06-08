package core.domain.item

import core.domain.status.ConditionType

sealed interface EffectKind : Item

/**
 * 回復itemに必要な変数
 */
interface HealEffect : EffectKind {
    /**
     * 回復量
     */
    val healAmount: Int
}

interface AttackEffect : EffectKind {
    val damageType: DamageType
}

interface ConditionEffect : EffectKind {
    val conditionType: ConditionType
}
