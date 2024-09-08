package battle.service.attack

import common.status.MonsterStatus
import common.status.Status

class DecMonsterHpService : DecHpService {
    override fun attack(
        target: Int,
        damage: Int,
        status: Status,
    ): Status {
        val monster = (status as MonsterStatus)

        return monster.copy(
            hp = monster.hp.copy(
                value = monster.hp.value - damage
            )
        )
    }
}
