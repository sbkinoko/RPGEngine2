package data.battle

import core.domain.BattleEventCallback
import core.domain.status.StatusData
import core.domain.status.StatusType
import core.domain.status.monster.MonsterStatus
import gamescreen.battle.domain.BattleBackgroundType

data class EventBattleData(
    val monsterList: List<Pair<MonsterStatus, StatusData<StatusType.Enemy>>>,
    val battleEventCallback: BattleEventCallback,
    val battleBackgroundType: BattleBackgroundType,
)
