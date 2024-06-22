package battle.domain

data class CommandState(
    private val commandStack: List<CommandType> = listOf(MainCommand),
) {
    val nowState: CommandType
        get() = commandStack.last()

    fun push(commandType: CommandType): CommandState {
        return CommandState(
            commandStack = commandStack + commandType
        )
    }

    fun pop(): CommandState {
        if (commandStack.size == 1) {
            return this
        }
        return CommandState(
            commandStack = commandStack.dropLast(1),
        )
    }
}
