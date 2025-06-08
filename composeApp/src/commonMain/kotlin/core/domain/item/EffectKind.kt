package core.domain.item

import core.domain.status.ConditionType
import core.domain.status.param.Buf
import core.domain.status.param.ParameterType

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

interface BufEffect : EffectKind {
    val parameterType: ParameterType
    val buf: Buf<*>
}
