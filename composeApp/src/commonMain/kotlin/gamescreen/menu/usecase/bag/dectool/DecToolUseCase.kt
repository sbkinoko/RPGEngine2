package gamescreen.menu.usecase.bag.dectool

import data.item.tool.ToolId

interface DecToolUseCase {
    operator fun invoke(
        itemId: ToolId,
        itemNum: Int,
    )
}
