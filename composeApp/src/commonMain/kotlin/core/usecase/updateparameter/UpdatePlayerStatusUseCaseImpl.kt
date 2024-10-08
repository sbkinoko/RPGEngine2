package core.usecase.updateparameter

import core.domain.status.PlayerStatus
import core.repository.status.StatusRepository

class UpdatePlayerStatusUseCaseImpl(
    override val statusRepository: StatusRepository<PlayerStatus>,
) : AbstractUpdateStatusUseCase<PlayerStatus>() {

    override fun decHPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
        return status.copy(
            hp = status.hp.copy(
                value = status.hp.value - amount
            )
        )
    }

    override fun incHPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
        return status.copy(
            hp = status.hp.copy(
                value = status.hp.value + amount
            )
        )
    }

    override fun decMPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
        return status.copy(
            mp = status.mp.copy(
                value = status.mp.value - amount
            )
        )
    }

    override fun incMPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
        return status.copy(
            mp = status.mp.copy(
                value = status.mp.value + amount
            )
        )
    }
}
