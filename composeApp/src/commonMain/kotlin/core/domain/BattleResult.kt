package core.domain

import core.domain.status.monster.MonsterStatus

enum class BattleResult {
    Win,
    Lose,
    None,
}

data class BattleEventCallback(
    val winCallback: () -> Unit,
    val loseCallback: () -> Unit,
) {
    fun callback(battleResult: BattleResult) {
        when (battleResult) {
            BattleResult.Win -> winCallback.invoke()
            BattleResult.Lose -> loseCallback.invoke()
            BattleResult.None -> Unit
        }
    }

    companion object {
        val default
            get() = BattleEventCallback(
                winCallback = {},
                loseCallback = {},
            )
    }
}

data class EventBattleData(
    val monsterList: List<MonsterStatus>,
    val battleEventCallback: BattleEventCallback,
)
