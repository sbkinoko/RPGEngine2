package data.event

import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import values.event.EventGroup1

class TalkEventManager1(
    private val textRepository: TextRepository,
    private val choiceRepository: ChoiceRepository,
) : AbstractEventManager<EventGroup1>() {
    override val flgName: String
        get() = "event1"

    override fun callEvent(key: EventGroup1) {

        lateinit var talkData: List<TextBoxData>

        talkData = listOf(
            TextBoxData(
                text = "お話中"
            ),
            TextBoxData(
                text = "選択肢表示",
                callBack = {
                    choiceRepository.push(
                        listOf(
                            Choice(
                                text = "何もしない",
                                callBack = {}
                            ),
                            Choice(
                                text = "繰り返す",
                                callBack = {
                                    textRepository.push(
                                        talkData
                                    )
                                }
                            ),
                        )
                    )
                }
            ),
        )

        textRepository.push(
            talkData,
        )
    }
}
