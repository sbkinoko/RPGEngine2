package core.usecase.item.usetool

import core.domain.item.CostType
import core.domain.item.tool.HealTool
import core.domain.status.PlayerStatus
import core.usecase.updateparameter.UpdatePlayerStatusUseCase
import core.usecase.updateparameter.UpdateStatusUseCase
import data.item.tool.ToolId
import data.item.tool.ToolRepository
import gamescreen.menu.usecase.bag.dectool.DecToolUseCase
import gamescreen.menu.usecase.gettoolid.GetToolIdUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import values.Constants

class UseToolUseCaseImpl(
    private val toolRepository: ToolRepository,
    private val updatePlayerStatusUseCase: UpdatePlayerStatusUseCase,
    private val updateStatusUseCase: UpdateStatusUseCase<PlayerStatus>,

    private val decToolUseCase: DecToolUseCase,
    private val getToolIdUseCase: GetToolIdUseCase,
) : UseToolUseCase {
    override fun invoke(
        userId: Int,
        index: Int,
        targetId: Int,
    ) {
        val toolId = getToolIdUseCase(
            userId = userId,
            index = index,
        )
        CoroutineScope(Dispatchers.IO).launch {
            val tool = toolRepository.getItem(
                toolId
            )

            when (tool.costType) {
                CostType.Consume -> {
                    delTool(
                        userId = userId,
                        index = index,
                        toolId = toolId,
                    )
                }

                // 消費しない道具
                CostType.NotConsume -> Unit

                is CostType.MP -> TODO("道具でMPを利用する物を作る")
            }

            when (tool) {
                is HealTool -> {
                    updateStatusUseCase.incHP(
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
        toolId: ToolId,
    ) {
        if (userId < Constants.playerNum) {
            updatePlayerStatusUseCase.deleteToolAt(
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
