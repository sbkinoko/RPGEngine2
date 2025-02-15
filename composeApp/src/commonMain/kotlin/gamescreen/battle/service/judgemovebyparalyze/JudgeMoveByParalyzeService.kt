package gamescreen.battle.service.judgemovebyparalyze

import core.domain.status.ConditionType
import kotlin.random.Random

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
