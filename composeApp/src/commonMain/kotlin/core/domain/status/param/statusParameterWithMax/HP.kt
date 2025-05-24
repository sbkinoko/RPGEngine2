package core.domain.status.param.statusParameterWithMax

data class HP(
    override val data: StatusParameterData,
) : StatusParameterWithMax<HP>(data) {

    constructor(
        maxValue: Int,
        value: Int = maxValue,
    ) : this(
        data = StatusParameterData(
            maxPoint = maxValue,
            point = value
        )
    )

    override fun incMax(amount: Int): HP {
        return copy(
            data = data.incMax(amount)
        )
    }

    override fun inc(amount: Int): HP {
        return copy(
            data = data.inc(amount)
        )
    }

    override fun dec(amount: Int): HP {
        return copy(
            data = data.dec(amount),
        )
    }

    override fun set(
        maxValue: Int,
        value: Int,
    ): HP {
        return copy(
            data = data.set(
                maxValue = maxValue,
                value = value,
            )
        )
    }
}
