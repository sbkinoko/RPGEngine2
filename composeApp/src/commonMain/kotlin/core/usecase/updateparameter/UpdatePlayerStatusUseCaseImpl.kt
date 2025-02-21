package core.usecase.updateparameter

import core.domain.status.ConditionType
import core.domain.status.PlayerStatus
import core.repository.status.StatusRepository

class UpdatePlayerStatusUseCaseImpl(
    override val statusRepository: StatusRepository<PlayerStatus>,
) : UpdatePlayerStatusUseCase() {
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

    override suspend fun addCondition(
        id: Int,
        conditionType: ConditionType,
    ) {
        val status = statusRepository.getStatus(id)
        val updated = status.copy(
            conditionList = status.conditionList + conditionType
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
