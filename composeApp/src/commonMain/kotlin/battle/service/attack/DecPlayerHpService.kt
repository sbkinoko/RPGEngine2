package battle.service.attack

import common.status.PlayerStatus
import common.status.Status

class DecPlayerHpService : DecHpService {
    override fun attack(
        target: Int,
        damage: Int,
        status: Status,
    ): Status {
        val player = (status as PlayerStatus)

        return player.copy(
            hp = player.hp.copy(
                value = player.hp.value - damage
            )
        )
    }
}
