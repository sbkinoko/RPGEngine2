package core.repository.battlemonster

import core.domain.status.monster.MonsterStatus
import kotlinx.coroutines.flow.StateFlow

/**
 * 任意のモンスターリストを返し易くするためのinterface
 * getMonsterList以外実装済み
 */
interface TestBattleMonsterRepository : BattleMonsterRepository {
    override val monsterListStateFLow: StateFlow<List<MonsterStatus>>
        get() = throw NotImplementedError()


    override fun setMonsters(monsters: List<MonsterStatus>) {
        throw NotImplementedError()
    }

    override fun reload() {
        throw NotImplementedError()
    }

    override fun getStatus(id: Int): MonsterStatus {
        throw NotImplementedError()
    }

    override suspend fun setStatus(id: Int, status: MonsterStatus) {
        throw NotImplementedError()
    }

}
