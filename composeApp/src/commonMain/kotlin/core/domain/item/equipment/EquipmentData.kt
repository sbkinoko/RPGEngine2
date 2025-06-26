package core.domain.item.equipment

import core.domain.item.Equipment
import core.domain.item.Item
import core.domain.status.StatusIncrease

data class EquipmentData(
    override val name: String,
    override val explain: String,
    val type: EquipmentType,
    val statusList: StatusIncrease,
) : Equipment, Item {
    constructor(
        name: String,
        explain: String,
        type: EquipmentType,
    ) : this(
        name = name,
        explain = explain,
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
