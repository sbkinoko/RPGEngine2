package data.item.tool

import core.domain.item.Tool
import data.item.ItemRepository

interface ToolRepository : ItemRepository<ToolId, Tool>
