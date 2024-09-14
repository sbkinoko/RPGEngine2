package battle.repository.battlemonster

import common.status.MonsterStatus
import kotlinx.coroutines.flow.MutableSharedFlow

class BattleMonsterRepositoryImpl : BattleMonsterRepository {
    override val monsterListFlow: MutableSharedFlow<List<MonsterStatus>> = MutableSharedFlow()
    private var monsterList = listOf<MonsterStatus>()

    override fun getStatus(id: Int): MonsterStatus {
        return monsterList[id]
    }

    override fun getMonsters(): List<MonsterStatus> {
        return monsterList.toList()
    }

    override suspend fun setMonsters(monsters: List<MonsterStatus>) {
        monsterList = monsters
        monsterListFlow.emit(monsterList)
    }

    override suspend fun setStatus(
        id: Int,
        status: MonsterStatus
    ) {
        monsterList = monsterList.mapIndexed { index, oldMonster ->
            if (index != id) {
                oldMonster
            } else {
                status
            }
        }
        monsterListFlow.emit(monsterList)
    }


    override suspend fun reload() {
        monsterListFlow.emit(monsterList)
    }
}
