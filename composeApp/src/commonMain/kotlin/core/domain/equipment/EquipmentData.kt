package core.domain.equipment

data class EquipmentData(
    val weapon: Equipment<EquipmentType.Weapon> = Equipment(EquipmentType.Weapon),
)
