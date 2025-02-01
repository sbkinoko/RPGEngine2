package gamescreen.menushop.domain

import data.item.tool.ToolId

data class ShopItem(
    val name: String,
    val price: Int,
    val explain: String,
    val itemId: ToolId,
)
