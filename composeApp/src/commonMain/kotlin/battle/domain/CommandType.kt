package battle.domain

sealed class CommandType

data object MainCommand : CommandType()

data object FinishCommand : CommandType()

data object EscapeCommand : CommandType()

class PlayerActionCommand(
    override val playerId: Int,
) : PlayerIdCommand, CommandType()

class SelectEnemyCommand(
    override val playerId: Int,
) : PlayerIdCommand, CommandType()

class SkillCommand(
    override val playerId: Int,
) : PlayerIdCommand, CommandType()

class SelectAllyCommand(
    override val playerId: Int,
) : PlayerIdCommand, CommandType()

data object AttackPhaseCommand : CommandType()

/**
 * 選択の時に誰がプレイヤーかを気にする必要があるコマンド
 */
interface PlayerIdCommand {
    val playerId: Int
}
