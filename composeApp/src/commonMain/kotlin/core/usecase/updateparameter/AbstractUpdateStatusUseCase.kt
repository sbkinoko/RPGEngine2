package core.usecase.updateparameter

import core.domain.status.ConditionType
import core.domain.status.Status
import core.repository.status.StatusRepository

abstract class AbstractUpdateStatusUseCase<T> : UpdateStatusUseCase<T> {
    abstract val statusRepository: StatusRepository<T>

    override suspend fun decHP(
        id: Int,
        amount: Int,
    ) {
        val status = statusRepository.getStatus(
            id = id,
        )
        val newStatus = decHPImpl(
            amount = amount,
            status = status,
        )
        statusRepository.setStatus(
            id = id,
            status = newStatus,
        )
    }

    protected abstract fun decHPImpl(
        amount: Int,
        status: T,
    ): T

    override suspend fun incHP(
        id: Int,
        amount: Int,
    ) {
        val status = statusRepository.getStatus(id)
        val newStatus = incHPImpl(
            amount = amount,
            status = status
        )
        statusRepository.setStatus(
            id = id,
            status = newStatus,
        )
    }

    protected abstract fun incHPImpl(
        amount: Int,
        status: T,
    ): T

    override suspend fun decMP(
        id: Int,
        amount: Int,
    ) {
        val status = statusRepository.getStatus(
            id = id,
        )
        val newStatus = decMPImpl(
            amount = amount,
            status = status,
        )
        statusRepository.setStatus(
            id = id,
            status = newStatus,
        )
    }

    protected abstract fun decMPImpl(
        amount: Int,
        status: T,
    ): T

    override suspend fun incMP(
        id: Int,
        amount: Int,
    ) {
        val status = statusRepository.getStatus(id)
        val newStatus = incMPImpl(
            amount = amount,
            status = status
        )
        statusRepository.setStatus(
            id = id,
            status = newStatus,
        )
    }

    protected abstract fun incMPImpl(
        amount: Int,
        status: T,
    ): T

    override suspend fun updateConditionList(
        id: Int,
        conditionList: List<ConditionType>,
    ) {
        val status = statusRepository.getStatus(id)

        // fixme 型を指定できるように修正
        val updated = (status as Status).updateConditionList(
            conditionList = conditionList,
        ) as T

        statusRepository.setStatus(
            id = id,
            status = updated,
        )
    }
}
