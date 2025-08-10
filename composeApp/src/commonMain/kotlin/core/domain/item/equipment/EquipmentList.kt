package core.domain.item.equipment

import data.repository.item.equipment.EquipmentId

data class EquipmentList(
    val weapon: EquipmentId = EquipmentId.None,
    val shield: EquipmentId = EquipmentId.None,
) {
    val list
        get() = listOf(
            weapon,
            shield,
        )
}
