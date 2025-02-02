package data.battle

import core.domain.BattleEventCallback
import data.monster.MonsterRepository
import gamescreen.battle.domain.BattleId
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository

class BattleDataRepositoryImpl(
    private val monsterRepository: MonsterRepository,
    private val textRepository: TextRepository,
) : BattleDataRepository {
    override fun getBattleMonsterData(battleId: BattleId): EventBattleData {
        return when (battleId) {
            BattleId.Battle1 -> EventBattleData(
                monsterList = listOf(
                    monsterRepository.getMonster(0)
                ),
                battleEventCallback = BattleEventCallback(
                    winCallback = {
                        textRepository.push(
                            textBoxData = TextBoxData(
                                text = "おめでとう"
                            )
                        )
                    },
                    loseCallback = {
                        textRepository.push(
                            textBoxData = TextBoxData(
                                text = "また挑戦してね"
                            )
                        )
                    },
                ),
            )
        }
    }
}
