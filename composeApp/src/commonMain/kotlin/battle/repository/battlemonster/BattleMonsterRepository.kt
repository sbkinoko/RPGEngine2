package battle.repository.battlemonster

import common.repository.status.StatusRepository
import common.status.MonsterStatus
import kotlinx.coroutines.flow.MutableSharedFlow

interface BattleMonsterRepository : StatusRepository<MonsterStatus> {

    val monsterListFlow: MutableSharedFlow<List<MonsterStatus>>

    fun getMonsters(): List<MonsterStatus>

    suspend fun setMonsters(monsters: List<MonsterStatus>)

    suspend fun reload()
}
