package gamescreen.menu.domain

import data.item.tool.ToolId

sealed class GiveResult {

    data class OK(val itemId: ToolId) : GiveResult()

    data class NG(val text: String) : GiveResult()
}
