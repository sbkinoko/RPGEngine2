package core.usecase.equipment

import core.domain.equipment.Equipment
import core.domain.equipment.EquipmentType

class EquipUseCaseImpl : EquipUseCase {

    override fun <T : EquipmentType> invoke(
        equipment: Equipment<T>,
    ) {
        TODO("Not yet implemented")
    }
}
