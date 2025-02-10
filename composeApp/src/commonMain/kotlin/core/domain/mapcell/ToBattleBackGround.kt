package core.domain.mapcell

import gamescreen.battle.domain.BattleBackgroundType

fun CellType.MonsterCell.toBattleBackGround(): BattleBackgroundType {
    return when (this) {
        CellType.Glass -> BattleBackgroundType.Glass
        CellType.Road -> BattleBackgroundType.Road
    }
}
