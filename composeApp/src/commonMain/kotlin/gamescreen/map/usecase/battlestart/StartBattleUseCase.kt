package gamescreen.map.usecase.battlestart

import core.domain.status.MonsterStatus

interface StartBattleUseCase {
    operator fun invoke(
        monsterList: List<MonsterStatus>,
    )
}
