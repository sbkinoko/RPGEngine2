package core.domain.item.equipment

sealed class EquipmentType {

    data object Weapon : EquipmentType()

    data object Shield : EquipmentType()
}
