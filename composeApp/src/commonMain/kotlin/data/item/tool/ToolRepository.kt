package data.item.tool

import core.domain.item.ItemKind
import data.item.ItemRepository

interface ToolRepository : ItemRepository {
    override fun getItem(id: Int): ItemKind.Tool
}
