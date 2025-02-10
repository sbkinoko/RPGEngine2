package core.repository.battlemonster

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

    override fun getMonsters(): List<MonsterStatus> {
        return monsterList.toList()
    }

    override fun setMonsters(monsters: List<MonsterStatus>) {
        val nameNum: MutableMap<String, Int> = mutableMapOf()
        monsters.map {
            if (nameNum[it.name] != null) {
                nameNum[it.name] = (nameNum[it.name]!! + 1)
            } else {
                nameNum[it.name] = 1
            }
        }

        val nameId: MutableMap<String, Int> = mutableMapOf()

        monsterList = monsters.map {
            if (nameNum[it.name] == 1) {
                return@map it
            }

            if (nameId[it.name] != null) {
                nameId[it.name] = (nameId[it.name]!! + 1)
            } else {
                nameId[it.name] = 1
            }

            it.copy(
                name = it.name + nameId[it.name]
            )
        }
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

    override fun setBackgroundType(backgroundType: BattleBackgroundType) {
        mutableBackgroundType.value = backgroundType
    }

    override fun reload() {
        monsterList = monsterList.toList()
    }
}
