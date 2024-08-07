package battle.repositoryimpl

import battle.repository.BattleMonsterRepository
import common.status.MonsterStatus
import kotlinx.coroutines.flow.MutableSharedFlow

class BattleMonsterRepositoryImpl : BattleMonsterRepository {
    override val monsterListFlow: MutableSharedFlow<List<MonsterStatus>> = MutableSharedFlow()
    private var monsterList = listOf<MonsterStatus>()

    override fun getMonster(id: Int): MonsterStatus {
        return monsterList[id]
    }

    override fun getMonsters(): List<MonsterStatus> {
        return monsterList.toList()
    }

    override suspend fun setMonster(monsters: List<MonsterStatus>) {
        monsterList = monsters
        monsterListFlow.emit(monsterList)
    }

    override suspend fun reload() {
        monsterListFlow.emit(monsterList)
    }
}
