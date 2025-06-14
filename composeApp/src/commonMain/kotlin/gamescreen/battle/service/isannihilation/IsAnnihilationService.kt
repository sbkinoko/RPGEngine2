package gamescreen.battle.service.isannihilation

import core.domain.status.Character

interface IsAnnihilationService {

    // fixme statusData使うように修正
    /**
     * 入力したstatusが全滅しているかどうかをチェックする
     */
    operator fun invoke(
        statusList: List<Character<*>>,
    ): Boolean
}
