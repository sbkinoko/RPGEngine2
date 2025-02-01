package gamescreen.menu.usecase.bag.addtool

import data.item.tool.ToolId

interface AddToolUseCase {
    operator fun invoke(
        toolId: ToolId,
        toolNum: Int,
    )
}
