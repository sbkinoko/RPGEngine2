package core.repository.battlemonster

import core.domain.status.monster.MonsterStatus
import core.repository.status.StatusRepository
import kotlinx.coroutines.flow.StateFlow

interface BattleInfoRepository : StatusRepository<MonsterStatus> {

    val monsterListStateFLow: StateFlow<List<MonsterStatus>>

    fun getMonsters(): List<MonsterStatus>

    fun setMonsters(monsters: List<MonsterStatus>)

    fun reload()
}
