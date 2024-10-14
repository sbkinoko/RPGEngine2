package core.usecase.item.usetool

import core.domain.item.tool.HealTool
import core.repository.item.tool.ToolRepository
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class UseToolUseCaseImpl(
    private val toolRepository: ToolRepository,
    private val updateStatusService: UpdatePlayerStatusUseCase,
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
                updateStatusService.deleteToolAt(
                    index = index,
                    playerId = userId,
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
}
