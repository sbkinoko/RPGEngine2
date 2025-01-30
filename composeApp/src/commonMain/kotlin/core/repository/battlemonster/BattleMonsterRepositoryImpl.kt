package core.repository.battlemonster

import core.domain.status.monster.MonsterStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BattleMonsterRepositoryImpl : BattleMonsterRepository {
    private var monsterList = listOf<MonsterStatus>()
        set(value) {
            field = value
            mutableMonsterListStateFLow.value = value
        }

    private val mutableMonsterListStateFLow =
        MutableStateFlow(monsterList)
    override val monsterListStateFLow: StateFlow<List<MonsterStatus>> =
        mutableMonsterListStateFLow.asStateFlow()


    override fun getStatus(id: Int): MonsterStatus {
        return monsterList[id]
    }

    override fun getMonsters(): List<MonsterStatus> {
        return monsterList.toList()
    }

    override fun setMonsters(monsters: List<MonsterStatus>) {
        monsterList = monsters
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
    }

    override fun reload() {
        monsterList = monsterList.toList()
    }
}
