package data.event.talk

import data.event.AbstractEventManager
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import values.event.EventGroup2

class TalkEventManager2(
    private val textRepository: TextRepository,
) : AbstractEventManager<EventGroup2>() {
    override val flgName: String
        get() = "test"

    override fun callEvent(key: EventGroup2) {
        var talkData: List<TextBoxData>

        when (key) {
            EventGroup2.Boy1 -> {
                talkData = listOf(
                    TextBoxData(
                        text = "ようこそ"
                    ),
                )
                updateFlg(1)
            }

            EventGroup2.Boy2 -> {
                talkData = if (mutableStateFlow.value == 0) {
                    listOf(
                        TextBoxData(
                            text = "先に左の人と話してね"
                        ),
                    )
                } else {
                    listOf(
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
