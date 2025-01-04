package gamescreen.map.usecase.event.actionevent

import gamescreen.map.data.BoxData
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import gamescreen.text.TextBoxData
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
            is EventType.Box -> {
                textRepository.push(
                    TextBoxData(
                        text = "宝箱を開けた",
                    )
                )
                addToolUseCase.invoke(
                    toolNum = 1,
                    toolId = BoxData.getItem(
                        id = eventType.id,
                    ),
                )
                eventType.id.hasItem = false
                //fixme 画面にすぐ反映できるようにする
            }

            is EventType.Talk -> {
                textRepository.push(
                    TextBoxData(
                        text = "お話中",
                    )
                )
            }
        }
    }
}
