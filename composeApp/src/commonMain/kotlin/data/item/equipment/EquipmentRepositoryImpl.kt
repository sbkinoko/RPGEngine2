package data.item.equipment

import core.domain.item.equipment.EquipmentData
import core.domain.item.equipment.EquipmentType
import core.domain.status.IncData
import core.domain.status.StatusIncrease

class EquipmentRepositoryImpl : EquipmentRepository {
    override fun getItem(id: EquipmentId): EquipmentData {
        return when (id) {
            EquipmentId.None -> EquipmentData(
                name = "なし",
                explain = "なにもつけてない",
                type = EquipmentType.Weapon,
                statusList = StatusIncrease.empty,
            )

            EquipmentId.Sword -> EquipmentData(
                name = "剣",
                explain = "剣",
                EquipmentType.Weapon,
                StatusIncrease.empty.copy(
                    atk = IncData(10),
                )
            )

            EquipmentId.Shield -> EquipmentData(
                name = "盾",
                explain = "盾",
                EquipmentType.Weapon,
                StatusIncrease.empty.copy(
                    def = IncData(10),
                )
            )
        }
    }
}
