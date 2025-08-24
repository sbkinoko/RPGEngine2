package core.repository.memory.character.battlemonster

import core.domain.status.monster.MonsterStatus
import gamescreen.battle.domain.BattleBackgroundType
import kotlinx.coroutines.flow.StateFlow

interface BattleInfoRepository : core.repository.memory.character.CharacterRepository<MonsterStatus> {

    val monsterListStateFLow: StateFlow<List<MonsterStatus>>

    val backgroundType: StateFlow<BattleBackgroundType>

    fun setMonsters(monsters: List<MonsterStatus>)

    fun setBackgroundType(backgroundType: BattleBackgroundType)

    fun reload()
}
