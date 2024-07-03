package battle.serviceimpl

import battle.service.AttackService
import common.status.MonsterStatus

class AttackMonsterService : AttackService {

    override fun attack(
        target: Int,
        damage: Int,
        monster: MonsterStatus,
    ): MonsterStatus {
        return monster.copy(
            hp = monster.hp.copy(
                value = monster.hp.value - damage
            )
        )
    }
}
