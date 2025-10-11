package data.event.talk

import data.event.EventManager
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import values.event.EventGroup2

class TalkEventManager2(
    private val textRepository: TextRepository,
) : EventManager<EventGroup2> {

    private val mutableStateFlow = MutableStateFlow(0)

    override val eventFlag: StateFlow<Int>
        get() = mutableStateFlow.asStateFlow()

    override fun callEvent(key: EventGroup2) {
        var talkData: List<TextBoxData>

        when (key) {
            EventGroup2.Boy1 -> {
                talkData = listOf(
                    TextBoxData(
                        text = "ようこそ"
                    ),
                )
                mutableStateFlow.value = 1
            }

            EventGroup2.Boy2 -> {
                if (mutableStateFlow.value == 0) {
                    talkData = listOf(
                        TextBoxData(
                            text = "先に左の人と話してね"
                        ),
                    )
                } else {
                    talkData = listOf(
                        TextBoxData(
                            text = "左の人と話したんだね"
                        ),
                    )
                }
            }
        }

        textRepository.push(
            talkData,
        )
    }
}
