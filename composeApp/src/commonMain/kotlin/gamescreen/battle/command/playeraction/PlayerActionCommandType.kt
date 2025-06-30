package gamescreen.battle.command.playeraction

import gamescreen.battle.command.CommandType

enum class PlayerActionCommandType(
    override val menuString: String,
) : CommandType<PlayerActionCommandType> {
    Normal("攻撃"),
    Skill("スキル"),
    Tool("道具");
}
