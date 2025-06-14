package core.usecase.updateparameter

import core.domain.status.Character
import core.domain.status.StatusType
import core.repository.status.StatusRepository

abstract class AbstractUpdateStatusUseCase<V : StatusType, T : Character<V>> : UpdateStatusUseCase<V> {
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
}
