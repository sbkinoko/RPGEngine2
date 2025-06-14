package core.usecase.updateparameter

import core.domain.item.BufEffect
import core.domain.status.ConditionType
import core.domain.status.StatusType
import core.domain.status.monster.MonsterStatus
import core.domain.status.param.ParameterType
import core.repository.status.StatusRepository

class UpdateMonsterStatusUseCase(
    override val statusRepository: StatusRepository<MonsterStatus>,
) : AbstractUpdateStatusUseCase<StatusType.Enemy, MonsterStatus>() {

    override fun decHPImpl(
        amount: Int,
        status: MonsterStatus,
    ): MonsterStatus {
        return status.copy(
            statusData = status.statusData.decHP(
                amount = amount,
            ),
        )
    }

    override fun incHPImpl(
        amount: Int,
        status: MonsterStatus,
    ): MonsterStatus {
        return status.copy(
            statusData = status.statusData.incHP(
                amount = amount,
            ),
        )
    }

    override fun decMPImpl(
        amount: Int,
        status: MonsterStatus,
    ): MonsterStatus {
        return status.copy(
            statusData = status.statusData.decMP(
                amount = amount,
            ),
        )
    }

    override fun incMPImpl(
        amount: Int,
        status: MonsterStatus,
    ): MonsterStatus {
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

    override suspend fun updateConditionList(
        id: Int,
        conditionList: List<ConditionType>,
    ) {
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

    override suspend fun addBuf(
        id: Int,
        buf: BufEffect,
    ) {
        val status = statusRepository.getStatus(id)

        val updated = when (buf.parameterType) {
            ParameterType.ATK -> {
                status.statusData.copy(
                    atk = status.statusData.atk.grantBuf(
                        buf = buf.buf
                    )
                )
            }

            ParameterType.DEF -> TODO()
            ParameterType.HP -> TODO()
            ParameterType.MP -> TODO()
            ParameterType.SPD -> TODO()
        }

        statusRepository.setStatus(
            id = id,
            status = status.copy(
                statusData = updated
            ),
        )
    }

    override suspend fun spendTurn(id: Int) {
        statusRepository.getStatus(id).let {
            statusRepository.setStatus(
                id = id,
                status = it.copy(
                    statusData = it.statusData.reduceBuf()
                )
            )
        }
    }
}
