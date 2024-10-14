package gamescreen.menu.item.tool.usecase

import core.domain.item.tool.HealTool
import core.repository.item.tool.ToolRepository
import core.usecase.updateparameter.UpdateStatusUseCase
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.item.repository.useitemid.UseItemIdRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class UseToolUseCaseImpl(
    private val targetRepository: TargetRepository,
    private val useItemIdRepository: UseItemIdRepository,
    private val toolRepository: ToolRepository,
    private val updateStatusService: UpdateStatusUseCase<*>,
) : UseToolUseCase {
    override fun invoke() {
        CoroutineScope(Dispatchers.IO).launch {
            val target = targetRepository.target
            val itemId = useItemIdRepository.itemId

            val tool = toolRepository.getItem(
                id = itemId
            )

            when (tool) {
                is HealTool -> {
                    updateStatusService.incHP(
                        id = target,
                        amount = tool.healAmount,
                    )
                }
            }
        }
    }
}
