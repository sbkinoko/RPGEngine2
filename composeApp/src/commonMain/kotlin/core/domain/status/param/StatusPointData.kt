package core.domain.status.param

data class StatusPointData(
    val maxPoint: Int,
    val point: Int = maxPoint,
) {
    private fun correct(): StatusPointData {
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

    fun incMax(amount: Int): StatusPointData {
        return copy(
            maxPoint = maxPoint + amount,
        )
    }

    fun inc(amount: Int): StatusPointData {
        return copy(
            point = point + amount,
        ).correct()
    }

    fun dec(amount: Int): StatusPointData {
        return copy(
            point = point - amount,
        ).correct()
    }

    fun set(
        value: Int = point,
        maxValue: Int = maxPoint,
    ): StatusPointData {
        return copy(
            point = value,
            maxPoint = maxValue,
        ).correct()
    }
}
