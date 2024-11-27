package core.repository.battlemonster

import core.domain.status.MonsterStatus
import core.repository.status.StatusRepository
import kotlinx.coroutines.flow.MutableSharedFlow

interface BattleMonsterRepository : StatusRepository<MonsterStatus> {
    // fixme stateFlowにしたい
    val monsterListFlow: MutableSharedFlow<List<MonsterStatus>>

    fun getMonsters(): List<MonsterStatus>

    suspend fun setMonsters(monsters: List<MonsterStatus>)

    suspend fun reload()
}
