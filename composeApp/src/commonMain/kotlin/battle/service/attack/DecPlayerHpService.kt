package battle.service.attack

import common.status.PlayerStatus

class DecPlayerHpService : DecHpService<PlayerStatus> {
    override fun attack(
        target: Int,
        damage: Int,
        status: PlayerStatus,
    ): PlayerStatus {
        return status.copy(
            hp = status.hp.copy(
                value = status.hp.value - damage
            )
        )
    }
}
