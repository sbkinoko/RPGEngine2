package core.domain.status.param

import core.domain.status.IncData

/**
 * @param baseValue ステータスの基本値
 * @param addBuf 加算で補正するバフ
 */
data class StatusParameter<T : ParameterType>(
    val baseValue: Int,
    val addBuf: List<Buf<BufType.Add>> = emptyList(),
) {
    val value
        get() = baseValue + addBuf.sumOf { it.value }

    fun inc(incData: IncData<T>): StatusParameter<T> {
        return copy(
            baseValue = baseValue + incData.value,
        )
    }

    fun grantBuf(
        buf: Buf<BufType>
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
            addBuf = addBuf.map {
                it.reduceTurn()
            }
        )
    }
}
