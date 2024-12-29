package gamescreen.map.usecase.battledecidemonster

import core.domain.status.MonsterStatus

interface DecideBattleMonsterUseCase {

    /**
     * 戦闘するモンスターのリストを返す
     */
    operator fun invoke(): List<MonsterStatus>
}
