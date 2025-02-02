package data.battle

import core.domain.BattleEventCallback
import core.domain.status.monster.MonsterStatus

data class EventBattleData(
    val monsterList: List<MonsterStatus>,
    val battleEventCallback: BattleEventCallback,
)
