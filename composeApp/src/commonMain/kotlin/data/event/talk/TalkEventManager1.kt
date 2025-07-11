package data.event.talk

import data.event.EventManager
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.flow.StateFlow

class TalkEventManager1(
    private val textRepository: TextRepository,
    private val choiceRepository: ChoiceRepository,
) : EventManager<TalkEventKey1> {

    // 状態に依存しないのでエラー
    override val eventFlag: StateFlow<Int>
        get() = throw NotImplementedError()

    override fun callEvent(key: TalkEventKey1) {

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
