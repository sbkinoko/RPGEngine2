package data.battle

import core.domain.status.monster.MonsterStatus
import gamescreen.battle.domain.BattleId

interface BattleDataRepository {

    fun getBattleMonsterData(
        battleId: BattleId
    ): List<MonsterStatus>

}
