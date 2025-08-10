package core.domain.status

import data.repository.monster.item.tool.ToolId

data class DropItemInfo(
    val toolId: ToolId,
    val probability: Int,
)
