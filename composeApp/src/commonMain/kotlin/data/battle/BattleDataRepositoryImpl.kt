package data.battle

import common.DefaultScope
import core.domain.BattleEventCallback
import core.usecase.heal.MaxHealUseCase
import data.event.Event
import data.monster.MonsterRepository
import gamescreen.battle.domain.BattleBackgroundType
import gamescreen.battle.domain.BattleId
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.launch

class BattleDataRepositoryImpl(
    private val monsterRepository: MonsterRepository,
    private val textRepository: TextRepository,

    private val maxHealUseCase: MaxHealUseCase,
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
                        textRepository.push(
                            textBoxData = TextBoxData(
                                text = "おめでとう",
                                callBack = { Event.eventFlag = 1 },
                            )
                        )
                    },
                    escapeCallback = {
                        textRepository.push(
                            textBoxData = TextBoxData(
                                text = "逃げるのも時には大事だね",
                                callBack = { Event.eventFlag = 1 },
                            )
                        )
                    },
                    loseCallback = {
                        textRepository.push(
                            textBoxData = TextBoxData(
                                text = "また挑戦してね",
                                callBack = {
                                    Event.eventFlag = 1
                                    DefaultScope.launch {
                                        maxHealUseCase.invoke()
                                    }
                                }
                            ),
                        )
                    },
                ),
            )
        }
    }
}
