package core.usecase.equipment

import core.domain.equipment.Equipment
import core.domain.equipment.EquipmentType

interface EquipUseCase {

    /**
     * @param target 装備をつけたいキャラ
     * @param equipment つけたい装備
     */
    suspend fun invoke(
        target: Int,
        equipment: Equipment,
    )
}
