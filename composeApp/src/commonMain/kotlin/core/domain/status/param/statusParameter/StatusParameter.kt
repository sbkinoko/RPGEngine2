package core.domain.status.param.statusParameter

data class StatusParameter<T : ParameterType>(
    private val parameter: StatusParameterData,
) {

    // 数値からパラメータを作るコンストラクタ
    constructor(value: Int) : this(
        parameter = StatusParameterData(value),
    )

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
