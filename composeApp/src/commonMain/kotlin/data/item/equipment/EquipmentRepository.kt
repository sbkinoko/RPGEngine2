package data.item.equipment

import core.domain.item.equipment.EquipmentData
import data.item.ItemRepository

interface EquipmentRepository : ItemRepository<EquipmentId, EquipmentData>
