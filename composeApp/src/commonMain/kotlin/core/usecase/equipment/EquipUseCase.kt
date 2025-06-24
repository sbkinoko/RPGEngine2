package core.usecase.equipment

import core.domain.equipment.Equipment
import core.domain.equipment.EquipmentType

interface EquipUseCase {
    fun <T : EquipmentType> invoke(
        equipment: Equipment<T>,
    )
}
