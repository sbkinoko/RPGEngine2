package core.domain.status

import kotlin.random.Random

sealed class ConditionType {
    class Paralysis(
        val probability: Int = 50,
        val cure: Int = 50,
    ) : ConditionType()

    // fixme 割合ダメージの毒も作る
    class Poison(
        val damage: Int = 5,
        val cure: Int = 50,
    ) : ConditionType()
}

/**
 * 確率によって治った麻痺を除去したリストを作成
 */
fun List<ConditionType>.tryCureParalyze(): List<ConditionType> = this.filter {
    // 麻痺以外は持ってくる
    if (it !is ConditionType.Paralysis) {
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
