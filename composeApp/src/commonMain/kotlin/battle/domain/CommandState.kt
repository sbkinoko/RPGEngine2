package battle.domain

data class CommandState(
    private val commandStack: List<CommandType> = emptyList()
) {
    val nowState: CommandType
        get() = commandStack.last()

    fun push(commandType: CommandType) {

    }

    fun pop() {

    }
}
