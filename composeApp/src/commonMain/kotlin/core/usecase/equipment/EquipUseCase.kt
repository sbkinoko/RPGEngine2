package core.usecase.equipment

import core.domain.equipment.Equipment
import core.domain.equipment.EquipmentType

interface EquipUseCase {

    /**
     * @param target 装備をつけたいキャラ
     * @param equipment つけたい装備
     */
    fun <T : EquipmentType> invoke(
        target: Int,
        equipment: Equipment<T>,
    )
}
