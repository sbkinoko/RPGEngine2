package core.domain.status

import kotlin.random.Random


sealed class ConditionType {
    /**
     * @param probability 動けない確率
     * @param cure 行動後に治る確率
     */
    data class Paralysis(
        val probability: Int = 50,
        override val cure: Int = 50,
    ) : ConditionType(), CureProb {
        override fun toString(): String {
            return " 麻痺\np  $probability\n  cure $cure"
        }
    }

    // fixme 割合ダメージの毒も作る
    data class Poison(
        val damage: Int = 5,
        override val cure: Int = 50,
    ) : ConditionType(), CureProb {
        override fun toString(): String {
            return " 毒\n  damage $damage\n  cure $cure"
        }
    }

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

fun List<ConditionType.Paralysis>.canMove(): Boolean {
    this.map {
        val rand = Random.nextInt(100)
        val prob = it.probability
        // 麻痺で動けない
        if (rand < prob) {
            return false
        }
    }

    // 麻痺状態になっているものの、動けた
    return true
}
