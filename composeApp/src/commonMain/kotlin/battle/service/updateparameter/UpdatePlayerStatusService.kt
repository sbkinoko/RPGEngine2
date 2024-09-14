package battle.service.updateparameter

import common.repository.status.StatusRepository
import common.status.PlayerStatus

class UpdatePlayerStatusService(
    override val statusRepository: StatusRepository<PlayerStatus>,
) : AbstractUpdateParameterService<PlayerStatus>() {

    override fun decHPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
        return status.copy(
            hp = status.hp.copy(
                value = status.hp.value - amount
            )
        )
    }

    override fun incHPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
        TODO("Not yet implemented")
    }

    override fun decMPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
        TODO("Not yet implemented")
    }

    override fun incMPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
        TODO("Not yet implemented")
    }
}
