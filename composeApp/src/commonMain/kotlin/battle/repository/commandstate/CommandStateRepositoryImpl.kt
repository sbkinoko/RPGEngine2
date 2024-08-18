package battle.repository.commandstate

import battle.domain.CommandType
import battle.domain.MainCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class CommandStateRepositoryImpl : CommandStateRepository {
    override val commandTypeFlow: MutableSharedFlow<CommandType> = MutableSharedFlow(replay = 1)
    override val nowCommandType: CommandType
        get() = commandTypeQueue.last()

    private var commandTypeQueue: List<CommandType> = listOf(MainCommand)

    override fun init() {
        commandTypeQueue = listOf(CommandStateRepository.INITIAL_COMMAND_STATE)
        CoroutineScope(Dispatchers.Default).launch {
            commandTypeFlow.emit(
                nowCommandType,
            )
        }
    }

    override fun push(commandType: CommandType) {
        commandTypeQueue = commandTypeQueue + commandType
        CoroutineScope(Dispatchers.Default).launch {
            commandTypeFlow.emit(
                nowCommandType,
            )
        }
    }

    override fun pop() {
        // 大きさが1ならpopしない
        if (commandTypeQueue.size == 1)
            return

        commandTypeQueue = commandTypeQueue.dropLast(1)
        CoroutineScope(Dispatchers.Default).launch {
            commandTypeFlow.emit(
                nowCommandType,
            )
        }
    }
}
