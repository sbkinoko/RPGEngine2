package gamescreen.map.usecase.event

import core.domain.TextBoxData
import core.repository.item.tool.ToolRepositoryImpl
import core.text.repository.TextRepository
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import values.EventType

class EventUseCaseImpl(
    private val textRepository: TextRepository,
    private val addToolUseCase: AddToolUseCase,
) : EventUseCase {
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
