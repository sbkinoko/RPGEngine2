package battle.service.attack

import common.status.PlayerStatus

class UpdatePlayerStatusService : UpdateParameterService<PlayerStatus> {
    override fun decHP(
        amount: Int,
        status: PlayerStatus,
    ): PlayerStatus {
        return status.copy(
            hp = status.hp.copy(
                value = status.hp.value - amount
            )
        )
    }

    override fun incHP(
        amount: Int,
        status: PlayerStatus,
    ): PlayerStatus {
        TODO("Not yet implemented")
    }

    override fun decMP(
        amount: Int,
        status: PlayerStatus,
    ): PlayerStatus {
        TODO("Not yet implemented")
    }

    override fun incMP(
        amount: Int,
        status: PlayerStatus,
    ) {
        TODO("Not yet implemented")
    }
}
