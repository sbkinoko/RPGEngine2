package gamescreen.battle.service.isactivesomeone

import core.domain.status.Status

interface IsAnnihilationService {
    /**
     * 入力したstatusが全滅しているかどうかをチェックする
     */
    operator fun invoke(
        statusList: List<Status>
    ): Boolean
}
