package battle.repository.commandstate

import battle.domain.BattleCommandType
import battle.domain.MainCommand
import core.repository.command.CommandRepository

interface CommandStateRepository : CommandRepository<BattleCommandType> {
    fun init()

    fun popTo(condition: (BattleCommandType) -> Boolean)

    companion object {
        val INITIAL_COMMAND_STATE: BattleCommandType = MainCommand
    }
}
