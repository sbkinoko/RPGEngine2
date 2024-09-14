package battle.service.updateparameter

import common.repository.status.StatusRepository
import common.status.MonsterStatus

class UpdateMonsterStatusService(
    override val statusRepository: StatusRepository<MonsterStatus>,
) : AbstractUpdateParameterService<MonsterStatus>() {

    override fun decHPImpl(amount: Int, status: MonsterStatus): MonsterStatus {
        return status.copy(
            hp = status.hp.copy(
                value = status.hp.value - amount
            )
        )
    }

    override fun incHPImpl(amount: Int, status: MonsterStatus): MonsterStatus {
        TODO("Not yet implemented")
    }

    override fun decMPImpl(amount: Int, status: MonsterStatus): MonsterStatus {
        return status.copy(
            mp = status.mp.copy(
                value = status.mp.value - amount
            )
        )
    }

    override fun incMPImpl(amount: Int, status: MonsterStatus): MonsterStatus {
        TODO("Not yet implemented")
    }
}
