package battle.domain

sealed class CommandType

data object MainCommand : CommandType()

class PlayerActionCommand(
    playerId: Int,
) : CommandType()
