package battle.repository.commandstate

import battle.domain.BattleCommandType
import battle.domain.MainCommand
import kotlinx.coroutines.flow.MutableSharedFlow

interface CommandStateRepository {
    val battleCommandTypeFlow: MutableSharedFlow<BattleCommandType>
    val nowBattleCommandType: BattleCommandType

    fun init()

    fun push(battleCommandType: BattleCommandType)

    fun pop()

    fun popTo(condition: (BattleCommandType) -> Boolean)

    companion object {
        val INITIAL_COMMAND_STATE: BattleCommandType = MainCommand
    }
}
