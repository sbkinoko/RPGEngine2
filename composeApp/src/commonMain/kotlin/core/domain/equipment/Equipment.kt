package core.domain.equipment

import core.domain.status.StatusIncrease

data class Equipment(
    val type: EquipmentType,
    val statusList: StatusIncrease,
) {
    constructor(
        type: EquipmentType,
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
