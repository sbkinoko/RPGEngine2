package core.repository.memory.character.battlemonster

import core.domain.status.monster.MonsterStatus
import gamescreen.battle.domain.BattleBackgroundType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BattleInfoRepositoryImpl : BattleInfoRepository {
    private var monsterList = listOf<MonsterStatus>()
        set(value) {
            field = value
            mutableMonsterListStateFLow.value = value
        }

    private val mutableMonsterListStateFLow =
        MutableStateFlow(monsterList)
    override val monsterListStateFLow: StateFlow<List<MonsterStatus>> =
        mutableMonsterListStateFLow.asStateFlow()

    private val mutableBackgroundType = MutableStateFlow(
        BattleBackgroundType.Glass
    )
    override val backgroundType: StateFlow<BattleBackgroundType>
        get() = mutableBackgroundType.asStateFlow()


    override fun getStatus(id: Int): MonsterStatus {
        return monsterList[id]
    }

    override fun getStatusList(): List<MonsterStatus> {
        return monsterList
    }

    override fun setMonsters(monsters: List<MonsterStatus>) {
        monsterList = monsters
    }

    override suspend fun setStatus(
        id: Int,
        status: MonsterStatus,
    ) {
        monsterList = monsterList.mapIndexed { index, oldMonster ->
            if (index != id) {
                oldMonster
            } else {
                status
            }
        }
    }

    override fun setBackgroundType(backgroundType: BattleBackgroundType) {
        mutableBackgroundType.value = backgroundType
    }

    override fun reload() {
        monsterList = monsterList.toList()
    }
}
