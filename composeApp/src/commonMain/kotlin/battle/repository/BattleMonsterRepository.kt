package battle.repository

import common.status.MonsterStatus
import kotlinx.coroutines.flow.MutableSharedFlow

interface BattleMonsterRepository {

    val monsterListFlow: MutableSharedFlow<List<MonsterStatus>>

    fun getMonster(id: Int): MonsterStatus

    suspend fun setMonster(monsters: MutableList<MonsterStatus>)

    suspend fun reload()
}
