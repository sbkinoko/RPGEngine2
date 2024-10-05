package gamescreen.battle.repository.commandstate

import core.repository.command.CommandRepository
import gamescreen.battle.domain.BattleCommandType
import gamescreen.battle.domain.MainCommand

interface CommandStateRepository : CommandRepository<BattleCommandType> {
    fun init()

    fun popTo(condition: (BattleCommandType) -> Boolean)

    companion object {
        val INITIAL_COMMAND_STATE: BattleCommandType = MainCommand
    }
}
