package core.usecase.updateparameter

import core.domain.item.BufEffect
import core.domain.status.ConditionType

interface UpdateStatusUseCase<T> {
    /**
     *  HPを減らして、更新後のステータスを返す
     */
    suspend fun decHP(
        id: Int,
        amount: Int,
    )

    /**
     *  HPを増やして、更新後のステータスを返す
     */
    suspend fun incHP(
        id: Int,
        amount: Int,
    )

    suspend fun decMP(
        id: Int,
        amount: Int,
    )

    suspend fun incMP(
        id: Int,
        amount: Int,
    )

    suspend fun addCondition(
        id: Int,
        conditionType: ConditionType,
    )

    suspend fun updateConditionList(
        id: Int,
        conditionList: List<ConditionType>,
    )

    suspend fun addBuf(
        id: Int,
        buf: BufEffect,
    )

    suspend fun spendTurn(
        id: Int,
    )
}
