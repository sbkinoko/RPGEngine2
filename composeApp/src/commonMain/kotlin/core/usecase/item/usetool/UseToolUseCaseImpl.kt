package core.usecase.item.usetool

import core.domain.item.tool.HealTool
import core.repository.item.tool.ToolRepository
import core.repository.player.PlayerRepository
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import gamescreen.menu.usecase.bag.dectool.DecToolUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import values.Constants

class UseToolUseCaseImpl(
    private val playerRepository: PlayerRepository,
    private val toolRepository: ToolRepository,
    private val updateStatusService: UpdatePlayerStatusUseCase,
    private val decToolUseCase: DecToolUseCase,
) : UseToolUseCase {
    override fun invoke(
        userId: Int,
        index: Int,
        targetId: Int,
    ) {
        val toolId = playerRepository.getTool(
            playerId = userId,
            index = index,
        )
        CoroutineScope(Dispatchers.IO).launch {
            val tool = toolRepository.getItem(
                toolId
            )

            if (tool.isReusable.not()) {
                delTool(
                    userId = userId,
                    index = index,
                    toolId = toolId,
                )
            }

            when (tool) {
                is HealTool -> {
                    updateStatusService.incHP(
                        id = targetId,
                        amount = tool.healAmount,
                    )
                }
            }
        }
    }

    private suspend fun delTool(
        userId: Int,
        index: Int,
        toolId: Int,
    ) {
        if (userId < Constants.playerNum) {
            updateStatusService.deleteToolAt(
                index = index,
                playerId = userId,
            )
        } else {
            decToolUseCase.invoke(
                itemId = toolId,
                itemNum = 1
            )
        }
    }
}
