package core.domain.status.param.statusParameterWithMax

data class StatusParameterData(
    val maxPoint: Int,
    val point: Int = maxPoint,
) {
    private fun correct(): StatusParameterData {
        if (point < 0) {
            return copy(
                point = 0,
            )
        }

        if (maxPoint < point) {
            return copy(
                point = maxPoint,
            )
        }

        return this
    }

    fun incMax(amount: Int): StatusParameterData {
        return copy(
            maxPoint = maxPoint + amount,
        )
    }

    fun inc(amount: Int): StatusParameterData {
        return copy(
            point = point + amount,
        ).correct()
    }

    fun dec(amount: Int): StatusParameterData {
        return copy(
            point = point - amount,
        ).correct()
    }

    fun set(
        value: Int = point,
        maxValue: Int = maxPoint,
    ): StatusParameterData {
        return copy(
            point = value,
            maxPoint = maxValue,
        ).correct()
    }
}
