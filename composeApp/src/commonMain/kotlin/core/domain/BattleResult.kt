package core.domain

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
