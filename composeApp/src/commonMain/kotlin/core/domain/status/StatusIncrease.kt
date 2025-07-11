package core.domain.status

import core.domain.status.param.ParameterType

data class StatusIncrease(
    val hp: IncData<ParameterType.HP>,
    val mp: IncData<ParameterType.MP>,
    val speed: IncData<ParameterType.SPD>,
    val atk: IncData<ParameterType.ATK>,
    val def: IncData<ParameterType.DEF>,
) {
    constructor(
        hp: Int,
        mp: Int,
        speed: Int,
        atk: Int,
        def: Int,
    ) : this(
        hp = IncData(hp),
        mp = IncData(mp),
        atk = IncData(atk),
        def = IncData(def),
        speed = IncData(speed),
    )

    companion object {
        val empty = StatusIncrease(
            hp = 0,
            mp = 0,
            atk = 0,
            def = 0,
            speed = 0,
        )
    }
}

@Suppress("unused")
data class IncData<T : ParameterType>(
    val value: Int,
)
