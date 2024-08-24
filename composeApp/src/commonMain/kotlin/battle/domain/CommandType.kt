package battle.domain

sealed class CommandType

data object MainCommand : CommandType()

data object FinishCommand : CommandType()

data object EscapeCommand : CommandType()

class PlayerActionCommand(
    val playerId: Int,
) : CommandType()

class SelectEnemyCommand(
    val playerId: Int,
) : CommandType()

data object AttackPhaseCommand : CommandType()
