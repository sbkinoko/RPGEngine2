package core.repository.item.tool

import core.domain.item.tool.Tool
import core.repository.item.ItemRepository

interface ToolRepository : ItemRepository {
    override fun getItem(id: Int): Tool

}
