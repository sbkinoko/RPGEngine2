package gamescreen.map.usecase.battlestart

import core.domain.BattleEventCallback
import core.domain.status.monster.MonsterStatus

interface StartBattleUseCase {
    operator fun invoke(
        monsterList: List<MonsterStatus>,
        battleEventCallback: BattleEventCallback = BattleEventCallback.default
    )
}
