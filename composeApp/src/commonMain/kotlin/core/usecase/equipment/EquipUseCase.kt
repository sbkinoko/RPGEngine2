package core.usecase.equipment

import data.item.equipment.EquipmentId

interface EquipUseCase {

    /**
     * @param target 装備をつけたいキャラ
     * @param equipmentId つけたい装備
     */
    suspend fun invoke(
        target: Int,
        equipmentId: EquipmentId,
    )
}
