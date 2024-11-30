package gamescreen.map.usecase.event.actionevent

import gamescreen.map.data.BoxData
import gamescreen.map.repository.backgroundcell.BackgroundRepository
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import values.EventType

class ActionEventUseCaseImpl(
    private val textRepository: TextRepository,
    private val addToolUseCase: AddToolUseCase,

    private val backgroundRepository: BackgroundRepository,
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
                CoroutineScope(Dispatchers.IO).launch {
                    // fixme 画面反映をすぐできるようにする
                    backgroundRepository.reload()
                }
            }
        }
    }
}
