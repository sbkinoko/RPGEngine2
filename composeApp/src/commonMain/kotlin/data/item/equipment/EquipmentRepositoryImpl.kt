package data.item.equipment

import core.domain.equipment.Equipment
import core.domain.equipment.EquipmentType
import core.domain.status.IncData
import core.domain.status.StatusIncrease

class EquipmentRepositoryImpl : EquipmentRepository {
    override fun getItem(id: EquipmentId): Equipment {
        return when (id) {
            EquipmentId.Sword -> Equipment(
                EquipmentType.Weapon,
                StatusIncrease.empty.copy(
                    atk = IncData(10),
                )
            )

            EquipmentId.Shield -> Equipment(
                EquipmentType.Weapon,
                StatusIncrease.empty.copy(
                    def = IncData(10),
                )
            )
        }
    }
}
