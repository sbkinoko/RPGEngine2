package data.event

import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import values.event.EventJob

class TalkJob(
    private val textRepository: TextRepository,
    private val choiceRepository: ChoiceRepository,
) : AbstractEventManager<EventJob>() {
    override val flgName: String
        get() = "test"

    override fun callEvent(key: EventJob) {

        val talkData: List<TextBoxData> = listOf(
            TextBoxData(
                text = "職業を変えますか？",
                callBack = {
                    choiceRepository.push(
                        listOf(
                            Choice(
                                text = "はい",
                                callBack = {
                                    textRepository.push(
                                        TextBoxData(
                                            text = "職業変えました"
                                        )
                                    )
                                }
                            ),
                            Choice(
                                text = "いいえ",
                                callBack = {
                                    textRepository.push(
                                        TextBoxData(
                                            text = "またお越しください"
                                        )
                                    )
                                }
                            ),
                        )
                    )
                }

            )
        )

        textRepository.push(
            talkData,
        )
    }
}
