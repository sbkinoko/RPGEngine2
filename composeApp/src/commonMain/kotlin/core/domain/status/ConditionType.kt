package core.domain.status

import kotlin.random.Random

sealed class ConditionType {
    data class Paralysis(
        val probability: Int = 50,
        override val cure: Int = 50,
    ) : ConditionType(), CureProb

    // fixme 割合ダメージの毒も作る
    data class Poison(
        val damage: Int = 5,
        override val cure: Int = 50,
    ) : ConditionType(), CureProb

    interface CureProb {
        val cure: Int
    }
}

/**
 * 確率によって治った状態異常リストを作成
 */
inline fun <reified T : ConditionType.CureProb> List<ConditionType>.tryCure(): List<ConditionType> =
    this.filter {
        // 指定した状態異常以外はそのまま
        if (it !is T) {
        return@filter true
    }

    val rand = Random.nextInt(100)
    // 治るなら除去
    rand >= it.cure
}

/**
 * リストに含まれる毒のダメージを加算
 */
fun List<ConditionType>.tryCalcPoisonDamage(): Int = this.map {
    if (it !is ConditionType.Poison) {
        return 0
    }
    it.damage
}.sum()
