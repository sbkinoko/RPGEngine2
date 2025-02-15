package core.domain.status

import kotlin.random.Random

sealed class ConditionType {
    class Paralysis(
        val probability: Int = 50,
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
