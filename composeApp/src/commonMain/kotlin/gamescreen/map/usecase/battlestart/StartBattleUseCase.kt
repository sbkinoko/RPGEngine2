package gamescreen.map.usecase.battlestart

import core.domain.BattleEventCallback
import core.domain.status.StatusData
import core.domain.status.StatusType
import core.domain.status.monster.MonsterStatus
import gamescreen.battle.domain.BattleBackgroundType

interface StartBattleUseCase {
    operator fun invoke(
        monsterList: List<Pair<MonsterStatus, StatusData<StatusType.Enemy>>>,
        battleEventCallback: BattleEventCallback = BattleEventCallback.default,
        backgroundType: BattleBackgroundType,
    )
}
