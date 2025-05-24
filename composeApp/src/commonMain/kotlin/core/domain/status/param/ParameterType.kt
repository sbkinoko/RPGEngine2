package core.domain.status.param

sealed class ParameterType {
    data object HP : ParameterType()
    data object MP : ParameterType()

    data object SPD : ParameterType()
    data object ATK : ParameterType()
    data object DEF : ParameterType()
}
