package gamescreen.battle.service.isannihilation

import core.domain.status.StatusData

class IsAnnihilationServiceImpl : IsAnnihilationService {
    override fun invoke(
        statusList: List<StatusData<*>>,
    ): Boolean {
        //どれか一つでもActiveであれば全滅はしていない
        return !statusList.any {
            it.isActive
        }
    }
}
