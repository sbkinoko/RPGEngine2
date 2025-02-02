package data.battle

import core.domain.EventBattleData
import gamescreen.battle.domain.BattleId

interface BattleDataRepository {

    fun getBattleMonsterData(
        battleId: BattleId
    ): EventBattleData
}
