package core.usecase.equipment

import core.domain.equipment.Equipment

class EquipUseCaseImpl : EquipUseCase {

    override suspend fun invoke(
        target: Int,
        equipment: Equipment,
    ) {
        TODO("Not yet implemented")
    }
}
