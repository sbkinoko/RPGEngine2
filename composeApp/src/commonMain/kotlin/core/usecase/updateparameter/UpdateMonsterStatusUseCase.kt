package core.usecase.updateparameter

import core.domain.status.ConditionType
import core.domain.status.monster.MonsterStatus
import core.repository.status.StatusRepository

class UpdateMonsterStatusUseCase(
    override val statusRepository: StatusRepository<MonsterStatus>,
) : AbstractUpdateStatusUseCase<MonsterStatus>() {

    override fun decHPImpl(amount: Int, status: MonsterStatus): MonsterStatus {
        return status.copy(
            statusData = status.statusData.decHP(
                amount = amount,
            ),
        )
    }

    override fun incHPImpl(amount: Int, status: MonsterStatus): MonsterStatus {
        return status.copy(
            statusData = status.statusData.incHP(
                amount = amount,
            ),
        )
    }

    override fun decMPImpl(amount: Int, status: MonsterStatus): MonsterStatus {
        return status.copy(
            statusData = status.statusData.decMP(
                amount = amount,
            ),
        )
    }

    override fun incMPImpl(amount: Int, status: MonsterStatus): MonsterStatus {
        return status.copy(
            statusData = status.statusData.incMP(
                amount = amount,
            ),
        )
    }

    override suspend fun addCondition(
        id: Int,
        conditionType: ConditionType,
    ) {
        val status = statusRepository.getStatus(id)
        val updated = status.copy(
            statusData = status.statusData.addConditionType(
                conditionType = conditionType,
            )
        )
        statusRepository.setStatus(
            id = id,
            status = updated,
        )
    }

    override suspend fun updateConditionList(id: Int, conditionList: List<ConditionType>) {
        val status = statusRepository.getStatus(id)
        val updated = status.copy(
            statusData = status.statusData.updateConditionList(
                conditionList = conditionList
            )
        )
        statusRepository.setStatus(
            id = id,
            status = updated,
        )
    }
}
