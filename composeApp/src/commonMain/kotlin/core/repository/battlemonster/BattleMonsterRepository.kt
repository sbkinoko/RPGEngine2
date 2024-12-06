package core.repository.battlemonster

import core.domain.status.MonsterStatus
import core.repository.status.StatusRepository
import kotlinx.coroutines.flow.StateFlow

interface BattleMonsterRepository : StatusRepository<MonsterStatus> {

    val monsterListStateFLow: StateFlow<List<MonsterStatus>>

    fun getMonsters(): List<MonsterStatus>

    suspend fun setMonsters(monsters: List<MonsterStatus>)

    suspend fun reload()
}
