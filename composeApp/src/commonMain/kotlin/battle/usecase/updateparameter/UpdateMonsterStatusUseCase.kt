package battle.usecase.updateparameter

import main.repository.status.StatusRepository
import main.status.MonsterStatus

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
