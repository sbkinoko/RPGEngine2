package core.domain.status.param

import common.error.StatusParameterException
import core.domain.status.IncData
import kotlin.math.max
import kotlin.math.min

data class StatusParameterWithMax<T : ParameterType>(
    val maxPoint: Int,
    val point: Int = maxPoint,
) {
    fun incMax(incData: IncData<T>): StatusParameterWithMax<T> {
        return copy(
            maxPoint = maxPoint + incData.value
        )
    }

    fun decMax(incData: IncData<T>): StatusParameterWithMax<T> {

        val newVal = maxPoint - incData.value

        if (newVal < 0) {
            throw StatusParameterException()
        }

        return set(maxValue = newVal)
    }


    fun inc(amount: Int): StatusParameterWithMax<T> {
        return copy(
            point = min(point + amount, maxPoint),
        )
    }

    fun dec(amount: Int): StatusParameterWithMax<T> {
        return copy(
            point = max(point - amount, 0),
        )
    }

    fun set(
        maxValue: Int = maxPoint,
        value: Int = point,
    ): StatusParameterWithMax<T> {
        return copy(
            point = min(value, maxValue),
            maxPoint = maxValue,
        )
    }

    override fun toString(): String {
        return "$point/$maxPoint"
    }
}
