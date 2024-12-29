package data.item.tool

import core.domain.item.tool.Tool
import data.item.ItemRepository

interface ToolRepository : ItemRepository {
    override fun getItem(id: Int): Tool
}
