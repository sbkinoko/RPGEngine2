package gamescreen.battle.service.isannihilation

import core.domain.status.Character

interface IsAnnihilationService {
    /**
     * 入力したstatusが全滅しているかどうかをチェックする
     */
    operator fun invoke(
        statusList: List<Character<*>>,
    ): Boolean
}
