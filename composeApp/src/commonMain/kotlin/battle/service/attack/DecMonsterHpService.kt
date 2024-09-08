package battle.service.attack

import common.status.MonsterStatus

class DecMonsterHpService : DecHpService<MonsterStatus> {
    override fun attack(
        target: Int,
        damage: Int,
        status: MonsterStatus,
    ): MonsterStatus {
        return status.copy(
            hp = status.hp.copy(
                value = status.hp.value - damage
            )
        )
    }
}
