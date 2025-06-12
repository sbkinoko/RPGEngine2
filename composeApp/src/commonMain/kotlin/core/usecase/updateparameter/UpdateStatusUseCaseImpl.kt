package core.usecase.updateparameter

import core.domain.item.BufEffect
import core.domain.status.ConditionType
import core.domain.status.param.ParameterType
import core.repository.statusdata.StatusDataRepository

class UpdateStatusUseCaseImpl<T>(
    private val statusDataRepository: StatusDataRepository,
) : UpdateStatusUseCase<T> {
    override suspend fun decHP(
        id: Int,
        amount: Int,
    ) {
        statusDataRepository.setStatusData(
            id = id,
            statusData = statusDataRepository.getStatusData(id).decHP(amount = amount)
        )
    }

    override suspend fun incHP(
        id: Int,
        amount: Int,
    ) {
        statusDataRepository.setStatusData(
            id = id,
            statusData = statusDataRepository.getStatusData(id).incHP(amount = amount)
        )
    }

    override suspend fun decMP(
        id: Int,
        amount: Int,
    ) {
        statusDataRepository.setStatusData(
            id = id,
            statusData = statusDataRepository.getStatusData(id).decMP(amount = amount)
        )
    }

    override suspend fun incMP(
        id: Int,
        amount: Int,
    ) {
        statusDataRepository.setStatusData(
            id = id,
            statusData = statusDataRepository.getStatusData(id).incMP(amount = amount)
        )
    }

    override suspend fun addCondition(
        id: Int,
        conditionType: ConditionType,
    ) {
        statusDataRepository.setStatusData(
            id = id,
            statusData = statusDataRepository.getStatusData(id).addConditionType(
                conditionType = conditionType,
            )
        )
    }

    override suspend fun updateConditionList(
        id: Int,
        conditionList: List<ConditionType>,
    ) {
        statusDataRepository.setStatusData(
            id = id,
            statusData = statusDataRepository.getStatusData(id).updateConditionList(
                conditionList = conditionList,
            )
        )
    }

    override suspend fun addBuf(
        id: Int,
        buf: BufEffect,
    ) {
        val statusData = statusDataRepository.getStatusData(id)

        val updated = when (buf.parameterType) {
            ParameterType.ATK -> {
                statusData.copy(
                    atk = statusData.atk.grantBuf(
                        buf = buf.buf
                    )
                )
            }

            ParameterType.DEF -> TODO()
            ParameterType.HP -> TODO()
            ParameterType.MP -> TODO()
            ParameterType.SPD -> TODO()
        }

        statusDataRepository.setStatusData(
            id = id,
            statusData = updated,
        )
    }

    override suspend fun spendTurn(id: Int) {
        statusDataRepository.setStatusData(
            id = id,
            statusData = statusDataRepository
                .getStatusData(id)
                .reduceBuf(),
        )
    }
}
