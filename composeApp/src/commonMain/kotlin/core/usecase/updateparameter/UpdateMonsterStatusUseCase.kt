package core.usecase.updateparameter

import core.domain.status.MonsterStatus
import core.repository.status.StatusRepository

class UpdateMonsterStatusUseCase(
    override val statusRepository: StatusRepository<MonsterStatus>,
) : AbstractUpdateStatusUseCase<MonsterStatus>() {

    override fun decHPImpl(amount: Int, status: MonsterStatus): MonsterStatus {
        return status.copy(
            hp = status.hp.copy(
                value = status.hp.value - amount
            )
        )
    }

    override fun incHPImpl(amount: Int, status: MonsterStatus): MonsterStatus {
        return status.copy(
            hp = status.hp.copy(
                value = status.hp.value + amount
            )
        )
    }

    override fun decMPImpl(amount: Int, status: MonsterStatus): MonsterStatus {
        return status.copy(
            mp = status.mp.copy(
                value = status.mp.value - amount
            )
        )
    }

    override fun incMPImpl(amount: Int, status: MonsterStatus): MonsterStatus {
        return status.copy(
            mp = status.mp.copy(
                value = status.mp.value + amount
            )
        )
    }
}
