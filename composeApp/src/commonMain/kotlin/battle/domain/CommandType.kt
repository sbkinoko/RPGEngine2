package battle.domain

sealed class CommandType

data object MainCommand : CommandType()

class PlayerActionCommand(
    val playerId: Int,
) : CommandType()
