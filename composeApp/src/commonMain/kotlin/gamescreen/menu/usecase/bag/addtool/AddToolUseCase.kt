package gamescreen.menu.usecase.bag.addtool

import data.item.tool.ToolId

interface AddToolUseCase<T : ToolId> {
    operator fun invoke(
        toolId: T,
        toolNum: Int,
    )
}
