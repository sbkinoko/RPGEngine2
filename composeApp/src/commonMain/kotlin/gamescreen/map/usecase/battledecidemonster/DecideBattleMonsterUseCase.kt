package gamescreen.map.usecase.battledecidemonster

import core.domain.status.StatusData
import core.domain.status.StatusType
import core.domain.status.monster.MonsterStatus
import gamescreen.map.domain.background.BackgroundCell

interface DecideBattleMonsterUseCase {

    /**
     * 戦闘するモンスターのリストを返す
     */
    operator fun invoke(
        backgroundCell: BackgroundCell,
    ): List<Pair<MonsterStatus, StatusData<StatusType.Enemy>>>
}
