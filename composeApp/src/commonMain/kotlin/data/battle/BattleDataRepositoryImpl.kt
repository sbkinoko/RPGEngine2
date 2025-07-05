package data.battle

import core.domain.BattleEventCallback
import data.event.EventManager
import data.event.battle.BattleEventKey
import data.monster.MonsterRepository
import gamescreen.battle.domain.BattleBackgroundType
import gamescreen.battle.domain.BattleId

class BattleDataRepositoryImpl(
    private val monsterRepository: MonsterRepository,

    private val eventManager: EventManager<BattleEventKey>,
) : BattleDataRepository {
    override fun getBattleMonsterData(battleId: BattleId): EventBattleData {
        return when (battleId) {
            BattleId.Battle1 -> EventBattleData(
                monsterList = List(5) {
                    monsterRepository.getMonster(0)
                },
                battleBackgroundType = BattleBackgroundType.Event,
                battleEventCallback = BattleEventCallback(
                    winCallback = {
                        eventManager.callEvent(
                            BattleEventKey.Win,
                        )
                    },
                    escapeCallback = {
                        eventManager.callEvent(
                            BattleEventKey.Escape,
                        )
                    },
                    loseCallback = {
                        eventManager.callEvent(
                            BattleEventKey.Lose,
                        )
                    },
                ),
            )
        }
    }
}
