package core.domain.status.param.statusParameter

sealed class ParameterType {
    data object SPD : ParameterType()
    data object ATK : ParameterType()
    data object DEF : ParameterType()
}
