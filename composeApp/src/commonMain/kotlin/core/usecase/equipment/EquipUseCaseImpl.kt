package core.usecase.equipment

import core.domain.equipment.Equipment
import core.domain.equipment.EquipmentType

class EquipUseCaseImpl : EquipUseCase {

    override fun <T : EquipmentType> invoke(
        target: Int,
        equipment: Equipment<T>,
    ) {
        TODO("Not yet implemented")
    }
}
