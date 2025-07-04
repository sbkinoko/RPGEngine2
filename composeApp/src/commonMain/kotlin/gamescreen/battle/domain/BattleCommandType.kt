package gamescreen.battle.domain

import core.domain.BattleResult

sealed class BattleCommandType

data object MainCommand : BattleCommandType()

class FinishCommand(
    val battleResult: BattleResult,
) : BattleCommandType()

data object EscapeCommand : BattleCommandType()

class PlayerActionCommand(
    override val playerId: Int,
) : PlayerIdCommand, BattleCommandType()

class SelectEnemyCommand(
    override val playerId: Int,
) : PlayerIdCommand, BattleCommandType()

class SkillCommand(
    override val playerId: Int,
) : PlayerIdCommand, BattleCommandType()

class ToolCommand(
    override val playerId: Int,
) : PlayerIdCommand, BattleCommandType()

class SelectAllyCommand(
    override val playerId: Int,
) : PlayerIdCommand, BattleCommandType()

data object AttackPhaseCommand : BattleCommandType()

/**
 * 選択の時に誰がプレイヤーかを気にする必要があるコマンド
 */
interface PlayerIdCommand {
    val playerId: Int
}
