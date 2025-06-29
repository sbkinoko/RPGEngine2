package core.domain

enum class BattleResult {
    Win,
    Lose,
    Escape,
    None,
}

data class BattleEventCallback(
    val winCallback: () -> Unit,
    val escapeCallback: () -> Unit,
    val loseCallback: () -> Unit,
) {
    fun callback(battleResult: BattleResult) {
        when (battleResult) {
            BattleResult.Win -> winCallback.invoke()
            BattleResult.Lose -> loseCallback.invoke()
            BattleResult.Escape -> escapeCallback.invoke()
            BattleResult.None -> Unit
        }
    }

    companion object {
        val default
            get() = BattleEventCallback(
                winCallback = {},
                escapeCallback = {},
                loseCallback = {},
            )
    }
}
