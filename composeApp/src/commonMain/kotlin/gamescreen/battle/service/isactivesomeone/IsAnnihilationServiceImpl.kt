package gamescreen.battle.service.isactivesomeone

import core.domain.status.Status

class IsAnnihilationServiceImpl : IsAnnihilationService {
    override fun invoke(
        statusList: List<Status>
    ): Boolean {
        //どれか一つでもActiveであれば全滅はしていない
        return !statusList.any {
            it.isActive
        }
    }
}
