package core.domain.equipment

import core.domain.status.StatusIncrease

data class Equipment<T : EquipmentType>(
    val type: T,
    val statusList: StatusIncrease,
) {
    constructor(
        type: T,
    ) : this(
        type = type,
        statusList = StatusIncrease(
            hp = 0,
            mp = 0,
            atk = 0,
            def = 0,
            speed = 0,
        ),
    )
}
