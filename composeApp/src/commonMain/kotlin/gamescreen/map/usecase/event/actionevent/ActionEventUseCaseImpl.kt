package gamescreen.map.usecase.event.actionevent

import gamescreen.map.usecase.movetowater.MoveToWaterUseCase
import gamescreen.map.usecase.settalk.SetTalkUseCase
import gamescreen.menu.usecase.bag.addtool.AddToolUseCase
import gamescreen.menushop.usecase.setshopitem.SetShopItemUseCase
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import values.event.BoxData
import values.event.EventType
import values.event.TalkEvent

class ActionEventUseCaseImpl(
    private val textRepository: TextRepository,
    private val addToolUseCase: AddToolUseCase,
    private val setShopItemUseCase: SetShopItemUseCase,
    private val setTalkUseCase: SetTalkUseCase,
    private val moveToWaterUseCase: MoveToWaterUseCase,
) : ActionEventUseCase {
    override fun invoke(
        eventType: EventType,
    ) {
        when (eventType) {
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

            is TalkEvent -> {
                setTalkUseCase.invoke(
                    talkEvent = eventType,
                )
            }

            is EventType.Shop -> {
                val textBoxData = listOf(
                    TextBoxData(
                        text = "いらっしゃい",
                        callBack = {
                            setShopItemUseCase.invoke(eventType.shopId)
                        }
                    )
                )

                textRepository.push(
                    textBoxDataList = textBoxData,
                )
            }

            EventType.Water -> {
                val textBoxData = TextBoxData(
                    text = "水上に出るイベント"
                )
                textRepository.push(
                    textBoxData,
                )
                CoroutineScope(Dispatchers.Default).launch {
                    moveToWaterUseCase.invoke()
                }
            }
        }
    }
}
