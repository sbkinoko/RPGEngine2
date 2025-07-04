package core.domain.status.param

import common.error.StatusParameterException
import core.domain.status.IncData
import kotlin.math.max

/**
 * @param baseValue ステータスの基本値
 * @param addBuf 加算で補正するバフ
 */
data class StatusParameter<T : ParameterType>(
    val baseValue: Int,
    val addBuf: List<Buf<BufType.Add>> = emptyList(),
) {
    val value
        get() = max(
            // 最小値は1
            baseValue + addBuf.sumOf { it.value },
            0,
        )

    fun inc(incData: IncData<T>): StatusParameter<T> {
        return copy(
            baseValue = baseValue + incData.value,
        )
    }

    fun dec(incData: IncData<T>): StatusParameter<T> {
        val newVal = baseValue - incData.value

        if (newVal < 0) {
            throw throw StatusParameterException()
        }

        return copy(
            baseValue = newVal,
        )
    }

    fun grantBuf(
        buf: Buf<*>,
    ): StatusParameter<T> {
        return when (buf.bufType) {
            BufType.Add -> {
                @Suppress("UNCHECKED_CAST")
                val castedBuf: Buf<BufType.Add> = buf as Buf<BufType.Add>
                copy(
                    addBuf = addBuf + castedBuf,
                )
            }
        }
    }

    fun reduceBuf(): StatusParameter<T> {
        return copy(
            addBuf = addBuf.mapNotNull {
                it.reduceTurn()
            },
        )
    }

    override fun toString(): String {
        var s = "\n base:$baseValue"
        s += "\n AddBuf"
        addBuf.forEach {
            s += "\n  ${it.value} ${it.rest}"
        }
        return s
    }
}
