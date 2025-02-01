package core.domain.status

import data.item.tool.ToolId

data class DropItemInfo(
    val toolId: ToolId,
    val probability: Int,
)
