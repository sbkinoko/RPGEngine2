package gamescreen.battle.service.isannihilation

import core.domain.status.Character

class IsAnnihilationServiceImpl : IsAnnihilationService {
    override fun invoke(
        statusList: List<Character>,
    ): Boolean {
        //どれか一つでもActiveであれば全滅はしていない
        return !statusList.any {
            it.statusData.isActive
        }
    }
}
