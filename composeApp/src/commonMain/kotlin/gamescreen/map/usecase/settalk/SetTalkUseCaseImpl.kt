package gamescreen.map.usecase.settalk

import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import values.event.TalkId

class SetTalkUseCaseImpl(
    private val textRepository: TextRepository,
    private val choiceRepository: ChoiceRepository,
) : SetTalkUseCase {
    override fun invoke(talkId: TalkId) {
        lateinit var talkData: List<TextBoxData>

        when (talkId) {
            TalkId.Talk1 -> talkData = listOf(
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

            TalkId.Talk2 ->
                talkData = listOf(
                )
        }


        textRepository.push(
            talkData,
        )
    }
}
