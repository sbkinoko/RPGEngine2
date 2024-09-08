package battle.repository.commandstate

import battle.domain.CommandType
import battle.domain.MainCommand
import kotlinx.coroutines.flow.MutableSharedFlow

interface CommandStateRepository {
    val commandTypeFlow: MutableSharedFlow<CommandType>
    val nowCommandType: CommandType

    fun init()

    fun push(commandType: CommandType)

    fun pop()

    fun popTo(condition: (CommandType) -> Boolean)

    companion object {
        val INITIAL_COMMAND_STATE: CommandType = MainCommand
    }
}
