package core.domain.item

import data.item.tool.ToolId

data class BagItemData<T : ToolId>(
    val id: T,
    val num: Int,
)
