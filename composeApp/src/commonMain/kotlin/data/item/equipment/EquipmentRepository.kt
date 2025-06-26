package data.item.equipment

import core.domain.equipment.Equipment
import data.item.ItemRepository

interface EquipmentRepository : ItemRepository<EquipmentId, Equipment>
