package gamescreen.map.usecase.settalk

import data.event.EventManager
import data.event.battle.BattleEventKey
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import values.event.TalkEvent

class SetTalkUseCaseImpl(
    private val textRepository: TextRepository,
    private val choiceRepository: ChoiceRepository,

    private val eventManager: EventManager<BattleEventKey>,
) : SetTalkUseCase {
    override fun invoke(talkEvent: TalkEvent) {

        when (talkEvent) {
            TalkEvent.Talk1 -> {
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

            TalkEvent.Talk2 -> eventManager.callEvent(BattleEventKey.Start)
        }
    }
}
