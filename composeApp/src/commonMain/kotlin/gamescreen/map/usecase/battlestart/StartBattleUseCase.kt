package gamescreen.map.usecase.battlestart

import core.domain.BattleEventCallback
import core.domain.status.monster.MonsterStatus
import gamescreen.battle.domain.BattleBackgroundType

interface StartBattleUseCase {
    operator fun invoke(
        monsterList: List<MonsterStatus>,
        battleEventCallback: BattleEventCallback = BattleEventCallback.default,
        backgroundType: BattleBackgroundType,
    )
}
