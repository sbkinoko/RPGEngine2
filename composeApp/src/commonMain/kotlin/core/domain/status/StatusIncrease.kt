package core.domain.status

import core.domain.status.param.statusParameter.ParameterType

data class StatusIncrease(
    val hp: Int,
    val mp: Int,
    val speed: IncData<ParameterType.SPD>,
    val atk: IncData<ParameterType.ATK>,
    val def: IncData<ParameterType.DEF>,
)

@Suppress("unused")
data class IncData<T : ParameterType>(
    val value: Int,
)
