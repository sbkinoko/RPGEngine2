package core.domain.status.param.statusParameterWithMax

data class MP(
    override val data: StatusParameterData,
) : StatusParameterWithMax<MP>(data) {
    constructor(
        maxValue: Int,
        value: Int = maxValue,
    ) : this(
        data = StatusParameterData(
            maxPoint = maxValue,
            point = value
        )
    )

    override fun incMax(amount: Int): MP {
        return copy(
            data = data.incMax(amount)
        )
    }

    override fun inc(amount: Int): MP {
        return copy(
            data = data.inc(amount)
        )
    }

    override fun dec(amount: Int): MP {
        return copy(
            data = data.dec(amount),
        )
    }

    override fun set(
        maxValue: Int,
        value: Int,
    ): MP {
        return copy(
            data = data.set(
                maxValue = maxValue,
                value = value,
            )
        )
    }
}
