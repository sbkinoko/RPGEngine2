package core.usecase.updateparameter

import core.domain.status.ConditionType
import core.domain.status.PlayerStatus
import core.repository.status.StatusRepository

class UpdatePlayerStatusUseCaseImpl(
    override val statusRepository: StatusRepository<PlayerStatus>,
) : UpdatePlayerStatusUseCase() {
    override fun decHPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
        return status.copy(
            statusData = status.statusData.decHP(
                amount,
            )
        )
    }

    override fun incHPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
        return status.copy(
            statusData = status.statusData.incHP(amount)
        )
    }

    override fun decMPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
        return status.copy(
            statusData = status.statusData.decMP(amount)
        )
    }

    override fun incMPImpl(amount: Int, status: PlayerStatus): PlayerStatus {
        return status.copy(
            statusData = status.statusData.incMP(amount)
        )
    }

    override suspend fun addCondition(
        id: Int,
        conditionType: ConditionType,
    ) {
        val status = statusRepository.getStatus(id)
        val updated = status.copy(
            statusData = status.statusData.addConditionType(
                conditionType,
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

    override suspend fun deleteToolAt(playerId: Int, index: Int) {
        val status = statusRepository.getStatus(
            id = playerId,
        ).copy(
            toolList = statusRepository.getStatus(
                id = playerId,
            ).toolList.filterIndexed { id, _ ->
                id != index
            }
        )

        statusRepository.setStatus(
            id = playerId,
            status = status,
        )
    }
}
