package battle.repositoryimpl

import battle.repository.BattleMonsterRepository
import common.status.MonsterStatus
import kotlinx.coroutines.flow.MutableSharedFlow

class BattleMonsterRepositoryImpl : BattleMonsterRepository {
    override val monsterListFlow: MutableSharedFlow<List<MonsterStatus>> = MutableSharedFlow()
    private var monsterList = mutableListOf<MonsterStatus>()

    override fun getMonster(id: Int): MonsterStatus {
        return monsterList[id]
    }

    override suspend fun setMonster(monsters: MutableList<MonsterStatus>) {
        monsterList = monsters
        monsterListFlow.emit(monsterList)
    }

    override suspend fun reload() {
        monsterListFlow.emit(monsterList)
    }
}
