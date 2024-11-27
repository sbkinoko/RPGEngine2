package gamescreen.map.usecase.event.actionevent

import core.domain.TextBoxData
import core.repository.item.tool.ToolRepositoryImpl
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import gamescreen.text.repository.TextRepository
import values.EventType

class ActionEventUseCaseImpl(
    private val textRepository: TextRepository,
    private val addToolUseCase: AddToolUseCase,
) : ActionEventUseCase {
    override fun invoke(
        eventType: EventType,
    ) {
        when (
            eventType
        ) {
            EventType.None -> Unit
            EventType.Box -> {
                textRepository.push(
                    TextBoxData(
                        text = "宝箱を開けた",
                    )
                )
                addToolUseCase.invoke(
                    toolNum = 1,
                    toolId = ToolRepositoryImpl.HEAL_TOOL2,
                )
            }
        }
    }
}
