package gamescreen.battle.command.escape

import gamescreen.battle.command.CommandType

enum class EscapeCommandType(
    override val menuString: String,
) : CommandType<EscapeCommandType> {
    Escape("逃げる"),
    BackToBattle("戦う");
}
