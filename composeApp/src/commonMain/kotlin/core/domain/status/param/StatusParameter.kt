package core.domain.status.param

import core.domain.status.IncData

/**
 * @param baseValue ステータスの基本値
 * // todo 補正用の項目を作る
 */
data class StatusParameter<T : ParameterType>(
    val baseValue: Int,
) {
    val value
        get() = baseValue

    fun inc(incData: IncData<T>): StatusParameter<T> {
        return copy(
            baseValue = baseValue + incData.value,
        )
    }
}
