package gamescreen.battle.command.main

import gamescreen.battle.command.CommandType
import values.TextData

enum class MainCommandType(
    override val menuString: String,
) : CommandType<MainCommandType> {
    Battle(TextData.BATTLE_MAIN_ATTACK),
    Escape(TextData.BATTLE_MAIN_ESCAPE),
    ;
}
