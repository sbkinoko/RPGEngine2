package core.domain.item.equipment

import core.domain.item.Equipment
import core.domain.status.StatusIncrease

data class EquipmentData(
    override val name: String,
    override val explain: String,
    val type: EquipmentType,
    val statusList: StatusIncrease,
) : Equipment
