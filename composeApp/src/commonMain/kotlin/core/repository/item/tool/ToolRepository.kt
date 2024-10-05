package core.repository.item.tool

import core.domain.item.tool.Tool

interface ToolRepository {
    fun getTool(id: Int): Tool

}
