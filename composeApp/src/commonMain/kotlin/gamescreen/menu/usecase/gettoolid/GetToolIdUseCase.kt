package gamescreen.menu.usecase.gettoolid

import data.item.tool.ToolId

interface GetToolIdUseCase {
    operator fun invoke(
        userId: Int,
        index: Int,
    ): ToolId
}
