package gamescreen.map.usecase.battleevent

import data.battle.BattleDataRepository
import gamescreen.battle.domain.BattleId
import gamescreen.map.usecase.battlestart.StartBattleUseCase

class StartEventBattleUseCaseImpl(
    private val battleDataRepository: BattleDataRepository,
    private val startBattleUseCase: StartBattleUseCase,
) : StartEventBattleUseCase {

    override fun invoke(battleId: BattleId) {
        val eventBattleData = battleDataRepository
            .getBattleMonsterData(
                battleId = battleId,
            )

        startBattleUseCase.invoke(
            monsterList = eventBattleData.monsterList,
            battleEventCallback = eventBattleData.battleEventCallback,
            backgroundType = eventBattleData.battleBackgroundType,
        )
    }
}
