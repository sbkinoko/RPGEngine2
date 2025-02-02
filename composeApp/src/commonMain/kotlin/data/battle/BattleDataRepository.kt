package data.battle

import gamescreen.battle.domain.BattleId

interface BattleDataRepository {

    fun getBattleMonsterData(
        battleId: BattleId
    ): EventBattleData
}
