package gamescreen.map.usecase.battleevent

import gamescreen.battle.domain.BattleId

interface StartEventBattleUseCase {

    operator fun invoke(
        battleId: BattleId,
    )
}
