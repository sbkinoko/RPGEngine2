package battle.domain

sealed class CommandType

data object MainCommand : CommandType()

data object FinishCommand : CommandType()

data object EscapeCommand : CommandType()

class PlayerActionCommand(
    override val playerId: Int,
) : PlayerIDCommand, CommandType()

class SelectEnemyCommand(
    override val playerId: Int,
) : PlayerIDCommand, CommandType()

class SkillCommand(
    override val playerId: Int,
) : PlayerIDCommand, CommandType()

data object AttackPhaseCommand : CommandType()

interface PlayerIDCommand {
    val playerId: Int
}
