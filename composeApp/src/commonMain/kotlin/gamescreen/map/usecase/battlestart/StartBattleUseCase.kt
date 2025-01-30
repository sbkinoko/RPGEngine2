package gamescreen.map.usecase.battlestart

import core.domain.status.monster.MonsterStatus

interface StartBattleUseCase {
    operator fun invoke(
        monsterList: List<MonsterStatus>,
    )
}
