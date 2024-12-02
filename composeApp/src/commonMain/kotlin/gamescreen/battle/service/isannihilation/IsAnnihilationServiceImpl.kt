package gamescreen.battle.service.isannihilation

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
