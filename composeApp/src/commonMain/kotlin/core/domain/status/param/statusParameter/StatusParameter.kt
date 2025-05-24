package core.domain.status.param.statusParameter

data class StatusParameter<T : ParameterType>(
    private val parameter: StatusParameterData,
) {
    val value
        get() = parameter.param

    fun inc(value: Int): StatusParameter<T> {
        return copy(
            parameter = parameter
                .copy(
                    param = parameter.param + value,
                )
        )
    }
}
