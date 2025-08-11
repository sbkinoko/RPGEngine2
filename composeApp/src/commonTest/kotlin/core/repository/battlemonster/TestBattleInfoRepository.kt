package core.repository.battlemonster

import core.domain.status.monster.MonsterStatus
import core.repository.character.battlemonster.BattleInfoRepository
import gamescreen.battle.domain.BattleBackgroundType
import kotlinx.coroutines.flow.StateFlow

/**
 * 任意のモンスターリストを返し易くするためのinterface
 * getMonsterList以外実装済み
 */
interface TestBattleInfoRepository : BattleInfoRepository {
    override val monsterListStateFLow: StateFlow<List<MonsterStatus>>
        get() = throw NotImplementedError()

    override val backgroundType: StateFlow<BattleBackgroundType>
        get() = throw NotImplementedError()

    override fun setBackgroundType(backgroundType: BattleBackgroundType) {
        throw NotImplementedError()
    }

    override fun setMonsters(monsters: List<MonsterStatus>) {
        throw NotImplementedError()
    }

    override fun reload() {
        throw NotImplementedError()
    }

    override fun getStatus(id: Int): MonsterStatus {
        throw NotImplementedError()
    }

    override suspend fun setStatus(
        id: Int,
        status: MonsterStatus,
    ) {
        throw NotImplementedError()
    }
}
