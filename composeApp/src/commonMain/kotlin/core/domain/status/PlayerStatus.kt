package core.domain.status

import core.domain.equipment.EquipmentData
import core.domain.status.param.EXP
import data.item.skill.SkillId
import data.item.tool.ToolId

data class PlayerStatus(
    val skillList: List<SkillId>,
    val toolList: List<ToolId>,
    val exp: EXP,
    val equipmentData: EquipmentData = EquipmentData(),
)
