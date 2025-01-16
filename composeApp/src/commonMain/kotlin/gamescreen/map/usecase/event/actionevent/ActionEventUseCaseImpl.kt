package gamescreen.map.usecase.event.actionevent

import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.map.data.BoxData
import gamescreen.mapshop.repoisitory.ShopMenuRepository
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import values.EventType

class ActionEventUseCaseImpl(
    private val textRepository: TextRepository,
    private val choiceRepository: ChoiceRepository,
    private val shopMenuRepository: ShopMenuRepository,
    private val addToolUseCase: AddToolUseCase,
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
                //fixme 画面にすぐ反映できるようにする
            }

            is EventType.Talk -> {
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

            is EventType.Shop -> {

                val textBoxData = listOf(
                    TextBoxData(
                        text = "いらっしゃい",
                        callBack = {
                            shopMenuRepository.setVisibility(
                                isVisible = true,
                            )
                        }
                    )
                )

                textRepository.push(
                    textBoxDataList = textBoxData,
                )
            }
        }
    }
}
