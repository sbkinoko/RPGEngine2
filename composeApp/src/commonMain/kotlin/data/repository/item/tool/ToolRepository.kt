package data.repository.item.tool

import core.domain.item.Tool
import data.repository.item.ItemRepository

interface ToolRepository : ItemRepository<ToolId, Tool>
