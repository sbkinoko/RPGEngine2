package gamescreen.menushop.domain

import data.repository.item.tool.ToolId

data class ShopItem(
    val name: String,
    val price: Int,
    val explain: String,
    val itemId: ToolId,
)
