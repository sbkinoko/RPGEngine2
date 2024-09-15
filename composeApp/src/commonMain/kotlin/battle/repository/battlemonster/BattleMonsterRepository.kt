package battle.repository.battlemonster

import kotlinx.coroutines.flow.MutableSharedFlow
import main.repository.status.StatusRepository
import main.status.MonsterStatus

interface BattleMonsterRepository : StatusRepository<MonsterStatus> {

    val monsterListFlow: MutableSharedFlow<List<MonsterStatus>>

    fun getMonsters(): List<MonsterStatus>

    suspend fun setMonsters(monsters: List<MonsterStatus>)

    suspend fun reload()
}
