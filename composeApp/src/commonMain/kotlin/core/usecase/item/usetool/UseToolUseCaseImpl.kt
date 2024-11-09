package core.usecase.item.usetool

import core.domain.item.tool.HealTool
import core.repository.item.tool.ToolRepository
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import gamescreen.menu.usecase.bag.dectool.DecToolUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import values.Constants

class UseToolUseCaseImpl(
    private val toolRepository: ToolRepository,
    private val updateStatusService: UpdatePlayerStatusUseCase,
    private val decToolUseCase: DecToolUseCase,
) : UseToolUseCase {
    override fun invoke(
        userId: Int,
        toolId: Int,
        index: Int,
        targetId: Int,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val tool = toolRepository.getItem(
                toolId
            )

            if (tool.isReusable.not()) {
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
}
