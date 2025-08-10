package gamescreen.menu.usecase.gettoolid

import data.repository.monster.item.tool.ToolId

interface GetToolIdUseCase {
    operator fun invoke(
        userId: Int,
        index: Int,
    ): ToolId
}
