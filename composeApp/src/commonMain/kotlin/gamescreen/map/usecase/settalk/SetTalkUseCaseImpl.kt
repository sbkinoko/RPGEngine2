package gamescreen.map.usecase.settalk

import data.event.Event
import gamescreen.battle.domain.BattleId
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.map.usecase.battleevent.StartEventBattleUseCase
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import values.event.TalkEvent

class SetTalkUseCaseImpl(
    private val textRepository: TextRepository,
    private val choiceRepository: ChoiceRepository,
    private val startEventBattleUseCase: StartEventBattleUseCase,
) : SetTalkUseCase {
    override fun invoke(talkEvent: TalkEvent) {
        lateinit var talkData: List<TextBoxData>

        when (talkEvent) {
            TalkEvent.Talk1 -> talkData = listOf(
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

            TalkEvent.Talk2 ->
                talkData = listOf(
                    TextBoxData(
                        text = if (Event.eventFlag == 0) {
                            "戦い"
                        } else {
                            "再戦"
                        },
                        callBack = {
                            startEventBattleUseCase.invoke(
                                battleId = BattleId.Battle1,
                            )
                        },
                    ),
                )
        }


        textRepository.push(
            talkData,
        )
    }
}
