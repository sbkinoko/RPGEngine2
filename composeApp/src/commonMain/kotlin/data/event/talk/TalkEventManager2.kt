package data.event.talk

import data.event.EventManager
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.flow.StateFlow

class TalkEventManager2(
    private val textRepository: TextRepository,
) : EventManager<TalkEventKey1> {

    // 状態に依存しないのでエラー
    override val eventFlag: StateFlow<Int>
        get() = throw NotImplementedError()

    override fun callEvent(key: TalkEventKey1) {

        val talkData: List<TextBoxData> = listOf(
            TextBoxData(
                text = "会話2"
            ),
        )

        textRepository.push(
            talkData,
        )
    }
}
