package data.repository.item.equipment

import core.domain.item.equipment.EquipmentData
import data.repository.monster.item.ItemRepository

interface EquipmentRepository : ItemRepository<EquipmentId, EquipmentData>
