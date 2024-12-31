package gamescreen.battle.repository.commandstate

import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.MainCommand
import kotlinx.coroutines.flow.StateFlow

interface CommandStateRepository {
    val commandStateFlow: StateFlow<BattleCommandType>
    val nowBattleCommandType: BattleCommandType

    fun init()

    fun push(battleCommandType: BattleCommandType)

    fun pop()

    fun popTo(condition: (BattleCommandType) -> Boolean)

    companion object {
        val INITIAL_COMMAND_STATE: BattleCommandType = MainCommand
    }
}
