package core.repository.battlemonster

import core.domain.status.monster.MonsterStatus
import core.repository.status.StatusRepository
import gamescreen.battle.domain.BattleBackgroundType
import kotlinx.coroutines.flow.StateFlow

interface BattleInfoRepository : StatusRepository<MonsterStatus> {

    val monsterListStateFLow: StateFlow<List<MonsterStatus>>

    val backgroundType: StateFlow<BattleBackgroundType>

    fun setMonsters(monsters: List<MonsterStatus>)

    fun setBackgroundType(backgroundType: BattleBackgroundType)

    fun reload()
}
