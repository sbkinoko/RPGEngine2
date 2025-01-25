package gamescreen.map.usecase.battleevent

import data.battle.BattleDataRepository
import gamescreen.battle.domain.BattleId
import gamescreen.map.usecase.battlestart.StartBattleUseCase

class StartEventBattleUseCaseImpl(
    private val battleDataRepository: BattleDataRepository,
    private val startBattleUseCase: StartBattleUseCase
) : StartEventBattleUseCase {
    override fun invoke(battleId: BattleId) {
        val monsters = battleDataRepository
            .getBattleMonsterData(
                battleId = BattleId.Battle1,
            )

        startBattleUseCase.invoke(
            monsterList = monsters
        )
    }
}
