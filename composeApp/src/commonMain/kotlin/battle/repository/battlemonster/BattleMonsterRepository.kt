package battle.repository.battlemonster

import common.status.MonsterStatus
import kotlinx.coroutines.flow.MutableSharedFlow

interface BattleMonsterRepository {

    val monsterListFlow: MutableSharedFlow<List<MonsterStatus>>

    fun getMonster(id: Int): MonsterStatus

    fun getMonsters(): List<MonsterStatus>

    suspend fun setMonster(monsters: List<MonsterStatus>)

    suspend fun reload()
}
