package data.battle

import data.event.EventManager
import data.event.battle.BattleEventKey
import data.monster.MonsterRepository
import gamescreen.battle.domain.BattleId

class BattleDataRepositoryImpl(
    private val monsterRepository: MonsterRepository,

    private val eventManager: EventManager<BattleEventKey>,
) : BattleDataRepository {
    override fun getBattleMonsterData(battleId: BattleId): EventBattleData {
        throw NotImplementedError()
        //        return when (battleId) {
        //            BattleId.Battle1 -> EventBattleData(
        //                monsterList =,
        //                battleBackgroundType =
        //                battleEventCallback =
        //            )
        //        }
    }
}
